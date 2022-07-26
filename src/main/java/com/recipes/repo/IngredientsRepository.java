package com.recipes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.bean.Ingredients;

public interface IngredientsRepository extends JpaRepository<Ingredients, Integer> {

}
