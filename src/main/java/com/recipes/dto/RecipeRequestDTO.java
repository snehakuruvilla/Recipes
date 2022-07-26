package com.recipes.dto;

import lombok.Data;

@Data
public class RecipeRequestDTO {

	private int recipeId;
	private int userId;
	private int servings;

}
