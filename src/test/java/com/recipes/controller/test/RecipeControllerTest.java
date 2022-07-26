package com.recipes.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.bean.FavoriteRecipes;
import com.recipes.bean.Recipes;
import com.recipes.bean.User;
import com.recipes.dto.FavoriteRecipeResonseDTO;
import com.recipes.dto.FavoriteRecipeSearchRequestDTO;
import com.recipes.dto.RecipeRequestDTO;
import com.recipes.dto.UpdateRecipeRequestDTO;
import com.recipes.services.RecipeService;
import com.recipes.test.BaseTest;

class RecipeControllerTest extends BaseTest{

	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	@MockBean
	private RecipeService recipeService;

	
	@Test
	void testGetAllRecipes() throws Exception{
		
		// set expectation
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
		
		//perform HTTP request and set the expectations with MockMVC
		when(recipeService.getAllRecipes()).thenReturn(recordsList);
			
		this.mockMvc.perform(get("/api/recipes/recipes"))
		                 .andExpect(status().isOk())
		                 .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                 .andExpect(jsonPath("$[0].recipeName", is("Food 1")))
		                 .andExpect(jsonPath("$[1].recipeName", is("Food 2")));
		           
	}	
	
	@Test
	void testGetAllFavRecipes() throws Exception{
		// set expectation
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
		
		//perform HTTP request and set the expectations with MockMVC
		when(recipeService.getAllFavoriteRecipeByUser(userId)).thenReturn(recordsList);
		
		this.mockMvc.perform(get("/api/recipes/favrecipes?userId={id}",userId))
		                      .andDo(print())
		                      .andExpect(status().isOk())
		                      .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                      .andExpect(jsonPath("$[1].rating", is(4)))
		                      .andExpect(jsonPath("$[1].dishType", is("NON-VEG")));
	}
		  

	@Test
	void testDeleteRecipe() throws Exception{
		// set expectation
		int favId = 1;
		FavoriteRecipes favRecipe = new FavoriteRecipes();
		favRecipe.setFavId(favId);
		favRecipe.setRating(5);
		favRecipe.setServings(2);
		//perform HTTP request and set the expectations with MockMVC
		when(recipeService.deleteRecipe(favId)).thenReturn(true);
		
		this.mockMvc.perform(delete("/api/recipes/deleterecipe/{id}",favRecipe.getFavId()))
		                    .andExpect(status().isNotFound());
	}
	
	
	@Test
	void testAddRecipe() throws Exception{
		// set expectation
		RecipeRequestDTO requestMock = new RecipeRequestDTO();
		requestMock.setRecipeId(1);
		requestMock.setServings(6);
		requestMock.setUserId(111);
		
		FavoriteRecipes record1 = new FavoriteRecipes();
		record1.setFavId(1);
		record1.setRating(4);
		record1.setServings(6);
		//perform HTTP request and set the expectations with MockMVC
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
		// set expectation
		int favId =1;
		
		UpdateRecipeRequestDTO request = new UpdateRecipeRequestDTO();
		request.setRating(6);
		request.setRecipeId(1);
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
		//perform HTTP request and set the expectations with MockMVC
		 when(recipeService.updateFavRecipe(favId,request)).thenReturn(favRecipe);		 
		 
		 mockMvc.perform(put(URI, favId)
						 .accept(MediaType.APPLICATION_JSON).content(inputInJson)
							.contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk()).andDo(print()).andReturn();

    }
	
	@Test
	void testSearching() throws Exception {
		// set expectation		
		FavoriteRecipeSearchRequestDTO request = new FavoriteRecipeSearchRequestDTO();
		request.setDishType("non-veg");
		request.setIncludeIngredient(Arrays.asList("pepper","tomato"));
		request.setExcludeIngredient(Arrays.asList("salt"));
		request.setInstruction("cooked");
		request.setServings(1);
		request.setUserId(112);
		
		FavoriteRecipeResonseDTO mockResponse = new FavoriteRecipeResonseDTO();
		mockResponse.setFavId(3);
		mockResponse.setRecipeId(1000);
		mockResponse.setRating(5);
		mockResponse.setServings(2);
		mockResponse.setDishType("NON-VEG");
		mockResponse.setInstruction("FULLY COOKED");
		
		FavoriteRecipeResonseDTO mockResponse2 = new FavoriteRecipeResonseDTO();
		mockResponse2.setFavId(3);
		mockResponse2.setRecipeId(1000);
		mockResponse2.setRating(4);
		mockResponse2.setServings(1);
		mockResponse2.setDishType("NON-VEG");
		mockResponse2.setInstruction("FULLY COOKED");
		
		
		List<FavoriteRecipeResonseDTO> recordsList = new ArrayList<>(Arrays.asList(mockResponse,mockResponse2));
		//perform HTTP request and set the expectations with MockMVC
        when(recipeService.searching(request)).thenReturn(recordsList);
        
        String URL = "/api/recipes/searching?dishType=non-veg&excludeIngredients=salt&includeIngredients=pepper&includeIngredients=tomato&instructions=cooked&servings=1&userId=112";
		
       when(recipeService.searching(request)).thenReturn(recordsList);
		
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
		                      .andDo(print())
		                      .andExpect(status().isOk())
		                      .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                      .andExpect(jsonPath("$[1].rating", is(4)))
		                      .andExpect(jsonPath("$[1].dishType", is("NON-VEG")));
        /*
        
        String inputInJson = this.mapToJson(mockResponse);
        this.mockMvc.perform(get(URL))
				//.accept(MediaType.APPLICATION_JSON).content(inputInJson))
				//.contentType(MediaType.APPLICATION_JSON))
		                      .andExpect(status().isOk())
		                      .andDo(print())
		                      .andExpect(jsonPath("$.size()", is(recordsList.size())))
		                      .andExpect(jsonPath("$[0].favId", is(3)))
		                      .andExpect(jsonPath("$[0].dishType", is("NON-VEG")));  */
	}
    
	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
	
	
	
