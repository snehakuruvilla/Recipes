package com.recipes.dto;

import lombok.Data;

@Data
public class FavoriteRecipeSearchRequestDTO {

	private int userId;
	private int servings;
	private String dishType;
	private String instruction;
	private String includeIngredient;
	private String excludeIngredient;

}
