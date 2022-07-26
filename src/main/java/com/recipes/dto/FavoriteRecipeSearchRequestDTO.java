package com.recipes.dto;

import java.util.List;

import lombok.Data;

@Data
public class FavoriteRecipeSearchRequestDTO {

	private int userId;
	private int servings;
	private String dishType;
	private String instruction;
	private List<String> includeIngredient;
	private List<String> excludeIngredient;

}
