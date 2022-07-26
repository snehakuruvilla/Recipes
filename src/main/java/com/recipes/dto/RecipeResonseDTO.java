package com.recipes.dto;

import java.util.List;

import com.recipes.bean.Ingredients;
import com.recipes.bean.Recipes;

import lombok.Data;

@Data
public class RecipeResonseDTO {

	List<Recipes> recipes;
	List<Ingredients> ingredients;

}
