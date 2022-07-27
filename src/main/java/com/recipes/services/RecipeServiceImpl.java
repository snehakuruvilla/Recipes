
package com.recipes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Ingredients;
import com.recipes.bean.Recipes;
import com.recipes.bean.User;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.FavoriteRecipeSearchRequestDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.exception.ResourceNotFoundException;
import com.recipes.repo.FavoriteRecipeRepository;
import com.recipes.repo.IngredientsRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.UserRepository;

/**
 * Service class for recipes functional implementions
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {
	
	private static final Logger log = LogManager.getLogger(RecipeServiceImpl.class);

	@Autowired
	FavoriteRecipeRepository favoriteRecipeRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IngredientsRepository ingredientsRepository;
	

	/**
	 * This method is used to get all the faviorite recipes per user.
	 * 
	 * The methods gets all the favorite recipe list from repository using findAll() method
	 * and filter out the favorite recipes for the given userId 
	 * Saves the list in the required formate in ResponseDTO
	 * 
	 */
	@Override
	public List<FavoriteRecipeResonseDTO> getAllFavoriteRecipeByUser(int userId) {
		log.info("Entering the getAllFavoriteRecipeByUser of RecipeServiceImpl class!!");
		return favoriteRecipeRepository.findAll().stream().filter(f -> f.getUser().getUserId() == userId)
				.map(this::convertEntityToDto).collect(Collectors.toList());
	}

	
	/**
	 * This method is used to get all the recipes
	 * 
	 * The method gets all the recipe list from the repository using the findAll() method.
	 */
	@Override
	public List<Recipes> getAllRecipes() {
		log.info("Entering the getAllRecipes of RecipeServiceImpl class!!");
		return recipeRepository.findAll();
	}
	

	/**
	 * This method is used to add a favorite recipe
	 * 
	 * This method Get the request from the UI and find the user and recipe object using the given respective ids
	 * and save the favorite recipe in to the repository
	 * 
	 */
	@Override
	public FavoriteRecipes addFavRecipe(RecipeRequestDTO favRecipeDto) throws Exception {
		
		log.info("Entering the addFavRecipe of RecipeServiceImpl class!!");
		
		User user = userRepository.findById(favRecipeDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + favRecipeDto.getUserId()));

		Recipes recipes = recipeRepository.findById(favRecipeDto.getRecipeId())
				.orElseThrow(() -> new ResourceNotFoundException("Recipes not found with id :" + favRecipeDto.getRecipeId()));

		FavoriteRecipes favoriteRecipe = new FavoriteRecipes();
		favoriteRecipe.setRecipe(recipes);
		favoriteRecipe.setUser(user);
		favoriteRecipe.setRating(recipes.getRating());
		favoriteRecipe.setServings(favRecipeDto.getServings());
		return favoriteRecipeRepository.save(favoriteRecipe);
		
	}
	
	

	/**
	 * This method is used to update recipe
	 * This method finds the record with Id using findById and updates the requested value in to the repository.
	 * 
	 * 
	 */
	@Override
	public FavoriteRecipes updateFavRecipe(int favId, UpdateRecipeRequestDTO updateRequest) throws Exception {
		
		log.info("Entering the updateFavRecipe of RecipeServiceImpl class!!");
		
		FavoriteRecipes favoriteRecipes = favoriteRecipeRepository.findById(favId)
				.orElseThrow(() -> new ResourceNotFoundException("FavoriteRecipes not found with id :" + favId));

		User user = userRepository.findById(updateRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + updateRequest.getUserId()));

		Recipes recipes = recipeRepository.findById(updateRequest.getRecipeId())
				.orElseThrow(() -> new ResourceNotFoundException("Recipes not found with id :" + updateRequest.getRecipeId()));

		favoriteRecipes.setRecipe(recipes);
		favoriteRecipes.setUser(user);
		favoriteRecipes.setServings(updateRequest.getServings());
		favoriteRecipes.setRating(updateRequest.getRating());
		return favoriteRecipeRepository.save(favoriteRecipes);

	}
	
	/**
	 * This method is used to delete a favorite recipe from list
	 * 
	 * Deletes the favoriteRecipe from the table using deleteById
	 */
	@Override
	public Boolean deleteRecipe(int favId) {
		log.info("Entering the deleteRecipe of RecipeServiceImpl class!!");
		favoriteRecipeRepository.deleteById(favId);
		return true;
		 
	}
	
	
	/**
	 * This method is used to get the filter search on dishType, servings, ingredients, instructions for User
	 * 
	 * Gets all the list of favoriteRecipe using findAll() and filter the result with the request.
	 * The result list is converted into the required format and passed to controller class.
	 *
	 */
	@Override
	public List<FavoriteRecipeResonseDTO> searching(FavoriteRecipeSearchRequestDTO favoriteRecipeSearchRequestDTO) {
		log.info("Entering the searching of RecipeServiceImpl class!!");
		
		return favoriteRecipeRepository.findAll().stream()
				.filter(t -> t.getUser().getUserId() == favoriteRecipeSearchRequestDTO.getUserId())
				.filter(t -> t.getServings() == favoriteRecipeSearchRequestDTO.getServings())
					.filter(t -> t.getRecipe().getDishType().equalsIgnoreCase(favoriteRecipeSearchRequestDTO.getDishType()))
					.filter(t -> t.getRecipe().getInstructions().toLowerCase().contains(favoriteRecipeSearchRequestDTO.getInstruction().toLowerCase()))
					.filter(t -> t.getRecipe().getIngredients().stream().anyMatch(i-> 
					favoriteRecipeSearchRequestDTO.getIncludeIngredient().stream()
					.anyMatch(b-> i.getIngredientsName().equalsIgnoreCase(b))))	
					.filter(t -> t.getRecipe().getIngredients().stream().anyMatch(i-> 
					favoriteRecipeSearchRequestDTO.getExcludeIngredient().stream()
					.anyMatch(b-> !i.getIngredientsName().equalsIgnoreCase(b))))	
				.map(this::convertEntityToDto).collect(Collectors.toList());

	}
	
	
	/**
	 * This method is used to convert entity to ResponseDTO
	 * 
	 * Mathod used to format the result according to the requirement
	 */
	private FavoriteRecipeResonseDTO convertEntityToDto(FavoriteRecipes favRecipes) {
		log.info("Entering the convertEntityToDto of RecipeServiceImpl class!!");
		FavoriteRecipeResonseDTO recipeResonseDTO = new FavoriteRecipeResonseDTO();
		recipeResonseDTO.setFavId(favRecipes.getFavId());
		recipeResonseDTO.setRecipeId(favRecipes.getRecipe().getRecipeId());
		recipeResonseDTO.setRecipeName(favRecipes.getRecipe().getRecipeName());
		recipeResonseDTO.setRating(favRecipes.getRating());
		recipeResonseDTO.setDishType(favRecipes.getRecipe().getDishType());
		recipeResonseDTO.setServings(favRecipes.getServings());
		recipeResonseDTO.setInstruction(favRecipes.getRecipe().getInstructions());
		recipeResonseDTO.setIngredients(this.getIngredients(favRecipes.getRecipe().getRecipeId()));
		return recipeResonseDTO;
	}
	
	/**
	 * This method is used to get the ingredients for each recipe 
	 * 
	 */
	private List<Ingredients> getIngredients(int recipeId){
		return recipeRepository.findAll().stream().filter(r -> r.getRecipeId() == recipeId)
				.flatMap(t -> t.getIngredients().stream()).distinct().collect(Collectors.toList());
	}

}
