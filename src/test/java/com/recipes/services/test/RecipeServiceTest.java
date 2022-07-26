package com.recipes.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.repo.FavoriteRecipeRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.services.RecipeService;
import com.recipes.test.BaseTest;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest extends BaseTest{

	@Autowired
    private RecipeService recipeService; 
	
	@Mock
    private RecipeRepository recipeRepository;
	
	@Mock
	private FavoriteRecipeRepository favoriteRecipeRepository;
	
	@Test
	public void testAddFavRecipe() throws Exception {
		
		RecipeRequestDTO requestMock = new RecipeRequestDTO();
		requestMock.setRecipe_id(1000);
		requestMock.setServings(6);
		requestMock.setUser_id(111);
		
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(1);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
		FavoriteRecipes storedUserDetails = recipeService.addFavRecipe(requestMock);
		assertNotNull(storedUserDetails);
		assertEquals(favRecipe.getServings(), storedUserDetails.getServings());
		
	}
	
	@Test
	public void testGetAllRecipes() {
		
		Recipes record = new Recipes();
		record.setRecipeName("OMLET");
		record.setRecipeId(1000);
		record.setRating(4);
		record.setDishType("NON-VEG");
		record.setInstructions("FULLY COOKED");
		
		List<Recipes> recordsList = new ArrayList();
		recordsList.add(record);

		List<Recipes> expected = recipeService.getAllRecipes();
		 assertEquals(expected.get(0).getRecipeName(),recordsList.get(0).getRecipeName());
   
	}
	
	@Test
	public void testGetAllFavoriteRecipeByUser() {
		
		int userId = 113;
		FavoriteRecipeResonseDTO response1 = new FavoriteRecipeResonseDTO();
		response1.setDishType("non-veg");
		response1.setRating(1);
		response1.setRecipeName("burger");
		
		FavoriteRecipeResonseDTO response2 = new FavoriteRecipeResonseDTO();
		response1.setDishType("non-veg");
		response1.setRating(1);
		response1.setRecipeName("burger");
		
	    List<FavoriteRecipeResonseDTO> recordsList = new ArrayList<>(Arrays.asList(response1, response2));
		
		List<FavoriteRecipeResonseDTO> expected = recipeService.getAllFavoriteRecipeByUser(userId);
		
	}
	
	@Test
	public void testUpdateRecipe() throws Exception {
		
		int favId = 1;
		UpdateRecipeRequestDTO request = new UpdateRecipeRequestDTO();
		request.setRating(6);
		request.setRecipe_id(1000);
		request.setServings(3);
		request.setUser_id(111);
		
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
		Mockito.when(favoriteRecipeRepository.getById(favId)).thenReturn(favRecipe);
		//given(favoriteRecipeRepository.save(favRecipe)).willReturn(favRecipe);
		
		Mockito.when(favoriteRecipeRepository.save(favRecipe)).thenReturn(favRecipe);

		assertThat(recipeService.updateFavRecipe(1,request)).isEqualTo(favRecipe);
	}
	
	@Test
	public void testDeleteTicket(){
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(1);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
	}
	
	
}
