
package com.recipes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipes.bean.FavoriteRecipes;
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
 * Service class to deal with recipes
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
	 * This method is used to get all the faviorite recipes per user
	 */
	@Override
	public List<FavoriteRecipeResonseDTO> getAllFavoriteRecipeByUser(int userId) {
		log.info("Entering the getAllFavoriteRecipeByUser of RecipeServiceImpl class!!");
		return favoriteRecipeRepository.findAll().stream().filter(f -> f.getUser().getUserId() == userId)
				.map(this::convertEntityToDto).collect(Collectors.toList());
	}

	/**
	 * This method is used to get all the recipes
	 */
	@Override
	public List<Recipes> getAllRecipes() {
		log.info("Entering the getAllRecipes of RecipeServiceImpl class!!");
		return recipeRepository.findAll();
	}
	
	/**
	 * This method is used to convert entity to ResponseDTO
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
		recipeResonseDTO.setIngredients(
				recipeRepository.findAll().stream().filter(r -> r.getRecipeId() == favRecipes.getRecipe().getRecipeId())
						.flatMap(t -> t.getIngredients().stream()).distinct().collect(Collectors.toList()));
		return recipeResonseDTO;
	}

	/**
	 * This method is used to add a favorite recipe
	 */
	@Override
	public FavoriteRecipes addFavRecipe(RecipeRequestDTO favRecipeDto) throws Exception {
		
		log.info("Entering the addFavRecipe of RecipeServiceImpl class!!");
		
		User user = userRepository.findById(favRecipeDto.getUser_id())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + favRecipeDto.getUser_id()));

		Recipes recipes = recipeRepository.findById(favRecipeDto.getRecipe_id())
				.orElseThrow(() -> new ResourceNotFoundException("Recipes not found with id :" + favRecipeDto.getRecipe_id()));

		FavoriteRecipes favoriteRecipe = new FavoriteRecipes();
		favoriteRecipe.setRecipe(recipes);
		favoriteRecipe.setUser(user);
		favoriteRecipe.setRating(recipes.getRating());
		favoriteRecipe.setServings(favRecipeDto.getServings());
		return favoriteRecipeRepository.save(favoriteRecipe);
		
	}

	/**
	 * This method is used to update recipe
	 */
	@Override
	public FavoriteRecipes updateFavRecipe(int favId, UpdateRecipeRequestDTO updateRequest) throws Exception {
		
		log.info("Entering the updateFavRecipe of RecipeServiceImpl class!!");
		
		FavoriteRecipes favoriteRecipes = favoriteRecipeRepository.findById(favId)
				.orElseThrow(() -> new ResourceNotFoundException("FavoriteRecipes not found with id :" + favId));

		User user = userRepository.findById(updateRequest.getUser_id())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + updateRequest.getUser_id()));

		Recipes recipes = recipeRepository.findById(updateRequest.getRecipe_id())
				.orElseThrow(() -> new ResourceNotFoundException("Recipes not found with id :" + updateRequest.getRecipe_id()));

		favoriteRecipes.setRecipe(recipes);
		favoriteRecipes.setUser(user);
		favoriteRecipes.setServings(updateRequest.getServings());
		favoriteRecipes.setRating(updateRequest.getRating());
		return favoriteRecipeRepository.save(favoriteRecipes);

	}
	
	/**
	 * This method is used to delete a favorite recipe from list
	 */
	@Override
	public FavoriteRecipes deleteRecipe(int favId) {
		log.info("Entering the deleteRecipe of RecipeServiceImpl class!!");
		favoriteRecipeRepository.deleteById(favId);
		return favoriteRecipeRepository.getById(favId);
		 
	}
	
	
	/**
	 * This method is used to get the filter search on dishType, servings, ingredients, instructions for User
	 *
	 */
	@Override
	public List<FavoriteRecipeResonseDTO> searching(FavoriteRecipeSearchRequestDTO favoriteRecipeSearchRequestDTO) {
		log.info("Entering the searching of RecipeServiceImpl class!!");
		return favoriteRecipeRepository.findAll().stream()
				.filter(f -> f.getUser().getUserId() == favoriteRecipeSearchRequestDTO.getUserId())
				.filter(t -> t.getServings() == favoriteRecipeSearchRequestDTO.getServings())
					.filter(t -> t.getRecipe().getDishType().equals(favoriteRecipeSearchRequestDTO.getDishType()))
					.filter(t -> t.getRecipe().getInstructions().equalsIgnoreCase(favoriteRecipeSearchRequestDTO.getInstruction()))
					/*.filter(t -> t.getRecipe().getIngredients().contains(favoriteRecipeSearchRequestDTO.getIncludeIngredient()))
					.filter(t -> !t.getRecipe().getIngredients()
						.contains(favoriteRecipeSearchRequestDTO.getExcludeIngredient())) */
				.map(this::convertEntityToDto).collect(Collectors.toList());
	}

}
