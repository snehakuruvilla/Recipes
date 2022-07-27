package com.recipes.dto;

import java.util.List;

import com.recipes.bean.Ingredients;

import lombok.Data;

/**
 * @author SK
 *
 */
@Data
public class FavoriteRecipeResonseDTO {

	private int favId;
	private int recipeId;
	private String recipeName;
	private int rating;
	private int servings;
	private String dishType;
	private String instruction;
	List<Ingredients> ingredients;

}
