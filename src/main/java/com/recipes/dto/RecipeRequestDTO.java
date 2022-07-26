package com.recipes.dto;

import lombok.Data;

@Data
public class RecipeRequestDTO {

	private int recipe_id;
	private int user_id;
	private int servings;

}
