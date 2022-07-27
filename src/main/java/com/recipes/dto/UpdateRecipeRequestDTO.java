package com.recipes.dto;

import lombok.Data;

/**
 * @author SK
 *
 */
@Data
public class UpdateRecipeRequestDTO {

	private int recipeId;
	private int userId;
	private int rating;
	private int servings;

}
