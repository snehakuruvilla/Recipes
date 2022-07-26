package com.recipes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recipes.bean.Recipes;

public interface RecipeRepository extends JpaRepository<Recipes , Integer>  {

}
