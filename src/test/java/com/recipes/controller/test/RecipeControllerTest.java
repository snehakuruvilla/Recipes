package com.recipes.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.bean.User;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.services.RecipeService;
import com.recipes.test.BaseTest;

public class RecipeControllerTest extends BaseTest{

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private RecipeService recipeService;
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	void testGetAllRecipes() throws Exception{
		
		Recipes record1 = new Recipes();
		record1.setRecipeName("Food 1");
		record1.setRecipeId(1000);
		record1.setRating(4);
		record1.setDishType("NON-VEG");
		record1.setInstructions("FULLY COOKED");
		
		Recipes record2 = new Recipes();
		record2.setRecipeName("Food 2");
		record2.setRecipeId(1008);
		record2.setRating(3);
		record2.setDishType("VEG");
		record2.setInstructions("LIGHT COOKED");
		
		List<Recipes> recordsList = new ArrayList<>(Arrays.asList(record1, record2));
		
		when(recipeService.getAllRecipes()).thenReturn(recordsList);
			
		this.mockMvc.perform(get("/api/recipes/recipes"))
		                 .andExpect(status().isOk())
		                 .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                 .andExpect(jsonPath("$[0].recipeName", is("Food 1")))
		                 .andExpect(jsonPath("$[1].recipeName", is("Food 2")));
		           
	}	
	
	@Test
	void testGetAllFavRecipes() throws Exception{
		
		FavoriteRecipeResonseDTO mockResponse1 = new FavoriteRecipeResonseDTO();
		mockResponse1.setFavId(3);
		mockResponse1.setRecipeId(1000);
		mockResponse1.setRating(4);
		mockResponse1.setServings(1);
		mockResponse1.setDishType("NON-VEG");
		mockResponse1.setInstruction("FULLY COOKED");
		
		FavoriteRecipeResonseDTO mockResponse2 = new FavoriteRecipeResonseDTO();
		mockResponse2.setFavId(3);
		mockResponse2.setRecipeId(1000);
		mockResponse2.setRating(4);
		mockResponse2.setServings(1);
		mockResponse2.setDishType("NON-VEG");
		mockResponse2.setInstruction("FULLY COOKED");
		
		FavoriteRecipeResonseDTO mockResponse3 = new FavoriteRecipeResonseDTO();
		mockResponse3.setFavId(3);
		mockResponse3.setRecipeId(1000);
		mockResponse3.setRating(4);
		mockResponse3.setServings(1);
		mockResponse3.setDishType("NON-VEG");
		mockResponse3.setInstruction("FULLY COOKED");
		
		int userId = 111;
		
		List<FavoriteRecipeResonseDTO> recordsList = new ArrayList<>(Arrays.asList(mockResponse1, mockResponse2,mockResponse3));
		
		when(recipeService.getAllFavoriteRecipeByUser(userId)).thenReturn(recordsList);
		
		this.mockMvc.perform(get("/api/recipes/favrecipes?userId={id}",userId))
		                     // .andDo(print())
		                      .andExpect(status().isOk())
		                      .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                      .andExpect(jsonPath("$[1].rating", is(4)))
		                      .andExpect(jsonPath("$[1].dishType", is("NON-VEG")));
	}
		  

	@Test
	void testDeleteRecipe() throws Exception{
		
		int favId = 1;
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(5);
		favRecipe.setServings(2);
		
		when(recipeService.deleteRecipe(favId)).thenReturn(favRecipe);
		
		this.mockMvc.perform(delete("/api/recipes/deleterecipe/{id}",favRecipe.getFavId()))
		                    .andExpect(status().isNotFound());
	}
	
	
	@Test
	void testAddRecipe() throws Exception{
		
		RecipeRequestDTO requestMock = new RecipeRequestDTO();
		requestMock.setRecipe_id(1);
		requestMock.setServings(6);
		requestMock.setUser_id(111);
		
		FavoriteRecipes record1 = new FavoriteRecipes();
		record1.setFavId(1);
		record1.setRating(4);
		record1.setServings(6);
		
		String inputInJson = this.mapToJson(requestMock);
		
		String URI = "/api/recipes/addrecipe";
		
		Mockito.when(recipeService.addFavRecipe(Mockito.any(RecipeRequestDTO.class))).thenReturn(record1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(requestBuilder).andExpect(status().isCreated())
		//.andDo(print())
		.andExpect(jsonPath("$.favId", is(1)))
		.andExpect(jsonPath("$.servings", is(6)))
		.andExpect(jsonPath("$.rating", is(4)));
		 
	} 
	
	
	@Test
    void testUpdateRecipe() throws Exception {
		
		int favId =1;
		
		UpdateRecipeRequestDTO request = new UpdateRecipeRequestDTO();
		request.setRating(6);
		request.setRecipe_id(1);
		request.setServings(3);
		
		User user = new User();
		user.setUserId(123);
		user.setUserName("test_user");
		
		Recipes recipe = new Recipes();
		recipe.setRecipeName("Food 1");
		recipe.setRecipeId(1000);
		recipe.setRating(4);
		recipe.setDishType("NON-VEG");
		recipe.setInstructions("FULLY COOKED");
		
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(6);
		favRecipe.setServings(3);
		favRecipe.setRecipe(recipe);
		favRecipe.setUser(user);
		
		String inputInJson = this.mapToJson(favRecipe);
		
		String URI = "/api/recipes/updaterecipe/{id}";
		
		 when(recipeService.updateFavRecipe(favId,request)).thenReturn(favRecipe);		 
		 
		 mockMvc.perform(put(URI, favId)
						 .accept(MediaType.APPLICATION_JSON).content(inputInJson)
							.contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk()).andDo(print()).andReturn();

    }
    
	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
	
	
	
