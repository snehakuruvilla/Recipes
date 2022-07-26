package com.recipes.dto;

import lombok.Data;

@Data
public class UpdateRecipeRequestDTO {

	private int recipe_id;
	private int user_id;
	private int rating;
	private int servings;

}
