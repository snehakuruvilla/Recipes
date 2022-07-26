package com.recipes.dto;

import lombok.Data;

@Data
public class UpdateRecipeRequestDTO {

	private int recipeId;
	private int userId;
	private int rating;
	private int servings;

}
