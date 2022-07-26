package com.recipes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.bean.FavoriteRecipes;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipes, Integer> {

}
