package com.recipes.services;

import java.util.List;

import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.FavoriteRecipeSearchRequestDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;

public interface RecipeService {

	List<FavoriteRecipeResonseDTO> getAllFavoriteRecipeByUser(int userId);

	List<Recipes> getAllRecipes();

	FavoriteRecipes addFavRecipe(RecipeRequestDTO favRecipeDto) throws Exception;

	FavoriteRecipes updateFavRecipe(int favId, UpdateRecipeRequestDTO updateRequest) throws Exception;

	FavoriteRecipes deleteRecipe(int favId);

	List<FavoriteRecipeResonseDTO> searching(FavoriteRecipeSearchRequestDTO request);

}
