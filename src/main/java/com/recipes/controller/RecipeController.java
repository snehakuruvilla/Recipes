package com.recipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.FavoriteRecipeSearchRequestDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.services.RecipeService;


/**
 * Rest controller /api/recipes  which exposes the end points -
 * 1. /favrecipe - to display all the favorite recipe of a user
 * 2. /recipes - to display all the recipes
 * 3. /addrecipe - to add recipe to the favorite recipe list
 * 4. /updaterecipes - to update a favorite recipe
 * 
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
		
	@Autowired
	RecipeService recipeService;
	
	
	/**
	 * This method is used to display all the favorite recipes for the user 
	 * @param userId
	 * @return RecipeResonseDTO
	 */
	@GetMapping("/favrecipes")
	public ResponseEntity<List<FavoriteRecipeResonseDTO>> getAllFavRecipes(@RequestParam(name="userId") int userId){
		return new ResponseEntity<>(recipeService.getAllFavoriteRecipeByUser(userId), HttpStatus.OK);
	}
	
	
	/**
	 * This method is used to display all the recipes 
	 *
	 * @return List<recipes>
	 */
	@GetMapping(value="/recipes")
	public ResponseEntity<List<Recipes>> getAllRecipes(){
		return new ResponseEntity<>(recipeService.getAllRecipes(),HttpStatus.OK);		
	}
	
	/**
	 * This method is used to add a new recipe to the favoriteRecipe 
	 * 
	 * @param favoriteRecipe object 
	 * @throws Exception 
	 */
	@PostMapping("/addrecipe")
	public ResponseEntity<FavoriteRecipes> addRecipe(@RequestBody RecipeRequestDTO favRecipe) throws Exception {
		return new ResponseEntity<>(recipeService.addFavRecipe(favRecipe), HttpStatus.CREATED);
	}
	
	/**
	 * This method is used to update a favorite recipe. 
	 * @param recipeId
	 * @param favoriteRecipe object 
	 * @return FavoriteRecipe object
	 */
	@PutMapping("/updaterecipe/{id}")
	public ResponseEntity<FavoriteRecipes> updateFavoriteRecipe(@RequestBody UpdateRecipeRequestDTO updateRequest,@PathVariable(name="id") int favId) throws Exception {
		return new ResponseEntity<>(recipeService.updateFavRecipe(favId,updateRequest), HttpStatus.OK);	
	}
	
	/**
	 * This method is used to delete recipe from favoriteRecipe 
	 * 
	 * @param id
	 * @return boolean
	 */
	@DeleteMapping("/deleterecipe/{id}")
	public ResponseEntity<Boolean> deleteRecipe(@PathVariable(name="id") int favId){
		return new ResponseEntity<>(recipeService.deleteRecipe(favId),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/searching")
	public ResponseEntity<List<FavoriteRecipeResonseDTO>> getSearching(@RequestParam(name="userId") int userId, @RequestParam(name="servings") int servings, 
			@RequestParam(name="dishType") String dishType, @RequestParam(name="instructions") String instructions, 
			@RequestParam(name="includeIngredients") List<String> includeIngredients, @RequestParam(name="excludeIngredients") List<String> excludeIngredients){
		FavoriteRecipeSearchRequestDTO request = new FavoriteRecipeSearchRequestDTO();
		request.setDishType(dishType);
		request.setExcludeIngredient(excludeIngredients);
		request.setIncludeIngredient(includeIngredients);
		request.setInstruction(instructions);
		request.setServings(servings);
		request.setUserId(userId);
		return new ResponseEntity<> (recipeService.searching(request),HttpStatus.OK);
	}

}
