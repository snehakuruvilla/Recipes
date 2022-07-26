package com.recipes.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.FavoriteRecipeSearchRequestDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.repo.FavoriteRecipeRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.services.RecipeService;
import com.recipes.test.BaseTest;


class RecipeServiceTest extends BaseTest{

	@Autowired
    private RecipeService recipeService; 
	
	@Mock
    private RecipeRepository recipeRepository;
	
	@Mock
	private FavoriteRecipeRepository favoriteRecipeRepository;
	
	@Test
	void testAddFavRecipe() throws Exception {
		//given
		RecipeRequestDTO requestMock = new RecipeRequestDTO();
		requestMock.setRecipeId(1000);
		requestMock.setServings(6);
		requestMock.setUserId(111);
		
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(1);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
		when(favoriteRecipeRepository.save(favRecipe)).thenReturn(favRecipe);
		//when
		FavoriteRecipes favoriteRecipes = recipeService.addFavRecipe(requestMock);
		//then
		assertNotNull(favoriteRecipes);
		assertEquals(favRecipe.getServings(), favoriteRecipes.getServings());
		
	}
	
	@Test
	void testGetAllRecipes() {
		//given
		Recipes record = new Recipes();
		record.setRecipeName("OMLET");
		record.setRecipeId(1000);
		record.setRating(4);
		record.setDishType("NON-VEG");
		record.setInstructions("FULLY COOKED");
		
		Recipes record2 = new Recipes();
		record2.setRecipeName("Food 2");
		record2.setRecipeId(1008);
		record2.setRating(3);
		record2.setDishType("VEG");
		record2.setInstructions("LIGHT COOKED");
		
		Recipes record3 = new Recipes();
		record3.setRecipeName("Food 2");
		record3.setRecipeId(1008);
		record3.setRating(3);
		record3.setDishType("VEG");
		record3.setInstructions("LIGHT COOKED");
		
		Recipes record4 = new Recipes();
		record4.setRecipeName("Food 2");
		record4.setRecipeId(1008);
		record4.setRating(3);
		record4.setDishType("VEG");
		record4.setInstructions("LIGHT COOKED");
		
		List<Recipes> recordsList = new ArrayList<>(Arrays.asList(record,record2,record3,record4));
		
		when(recipeRepository.findAll()).thenReturn(recordsList);
	    //when
		List<Recipes> expected = recipeService.getAllRecipes();
		//then
		assertEquals(4, expected.size());
		assertEquals(expected.get(0).getRecipeName(),recordsList.get(0).getRecipeName());
   
	}
	
	@Test
	void testGetAllFavoriteRecipeByUser() {
		//given
		int userId = 113;
		
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(1);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
		List<FavoriteRecipes> favRecipeList = new ArrayList<>(Arrays.asList(favRecipe));
		
		when(favoriteRecipeRepository.findAll()).thenReturn(favRecipeList);
		//when
		List<FavoriteRecipeResonseDTO> expected = recipeService.getAllFavoriteRecipeByUser(userId);
		//then
		assertEquals(expected.get(0).getRating(),favRecipeList.get(0).getRating());
	}
	
	@Test
	void testUpdateRecipe() throws Exception {
		//given
		int favId = 1;
		UpdateRecipeRequestDTO request = new UpdateRecipeRequestDTO();
		request.setRating(10);
		request.setRecipeId(1000);
		request.setServings(15);
		request.setUserId(111);
		
			
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(6);
		favRecipe.setServings(3);
		
		when(favoriteRecipeRepository.getById(favId)).thenReturn(favRecipe);
		favRecipe.setRating(10);
		favRecipe.setServings(15);
		
        when(favoriteRecipeRepository.save(favRecipe)).thenReturn(favRecipe);
        
        //when
        FavoriteRecipes favRecipeUpdate = recipeService.updateFavRecipe(favId,request);

        //then
        assertEquals(10, favRecipeUpdate.getRating());
        assertEquals(15, favRecipeUpdate.getServings());
		
		}
	
	@Test
	void testDeleteTicket(){
		//Given
		int favId =1;
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(3);
		favRecipe.setServings(6);
		
		//When
		favoriteRecipeRepository.deleteById(favId);
		//Then
		Mockito.verify(favoriteRecipeRepository).deleteById(favId);
	}
	
	@Test
	void testSearching() {
		//Given
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(1);
		favRecipe.setRating(4);
		favRecipe.setServings(6);
		List<FavoriteRecipes> favRecipeList = new ArrayList<>(Arrays.asList(favRecipe));
		
		FavoriteRecipeSearchRequestDTO request = new FavoriteRecipeSearchRequestDTO();
		request.setDishType("non-veg");
		request.setIncludeIngredient(Arrays.asList("pepper","tomato"));
		request.setExcludeIngredient(Arrays.asList("salt"));
		request.setInstruction("cooked");
		request.setServings(1);
		request.setUserId(112);
		
		FavoriteRecipeResonseDTO mockResponse1 = new FavoriteRecipeResonseDTO();
		mockResponse1.setFavId(3);
		mockResponse1.setRecipeId(1000);
		mockResponse1.setRating(4);
		mockResponse1.setServings(1);
		mockResponse1.setDishType("NON-VEG");
		mockResponse1.setInstruction("FULLY COOKED");
		List<FavoriteRecipeResonseDTO> recordsList = new ArrayList<>(Arrays.asList(mockResponse1)); 
	    
		when(favoriteRecipeRepository.findAll()).thenReturn(favRecipeList);
		//when
		List<FavoriteRecipeResonseDTO> expected = recipeService.searching(request);
		//then
		assertEquals(expected.get(0).getRating(),recordsList.get(0).getRating());
	}
	
}
