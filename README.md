###RECIPE API ####Java Spring Boot Application

#####Description: The assessment consists of an APIs to fulfill recipe related functionality for existing customers.

#####Requirements:

The APIs will allows users to manage their favorite recipes. It allows adding, updating, removing ,fetching and filter search recipes.

#####Pre Assumptions:

The existing users have atleast one of Favorite recipe.
There is a Recipe table which has all the recipes listed along Ingredients mapped.

#####Tech Stack

Java 8+
Spring Boot
H2 In memory database
JUnit 5
Mockito
Maven
Swagger


#####How to run

$ import the project Banking
$ convert to maven
$ run as maven build -- clean install
$ run as spring boot app
$ application swagger will be available in: localHost:8080/swagger-ui/

######Following are the Test UserID

111
112

#####Table Details:

USER --> To store the existing users
RECIPES --> To store the recipes.
FAVORITE_RECIPES --> To store the favorite recipes of users.
INGREDIENTS --> To list all the Ingredients.
RECIPE_INGREDIENTS --> To map the Ingredients with recipes

#####The application apis

GET /api/recipes/recipes - To get all recipes.
GET /api/recipes/favrecipes - To get the favorite recipes of user.
GET ​/api​/recipes​/searching - To get the filter search result.
POST /api/recipes/addrecipe - add a new favorite recipe for user.
PUT ​/api​/recipes​/updaterecipe​/{id} - Update the favorite recipe details.
DELETE /api/recipes/deleterecipe/{id} - Delete a favorite recipe for a user.

JUnit test are available. Code quality checked using Sonar.

####REMARKS

#####Expected Frontend: 

########UserHome page:
---> A userHome page where all the favorite Recipes will be listed as a table -- which invokes the api GET /api/recipes/favrecipes 
---> along with update and delete button on each record -- which invokes the DELETE and PUT method
---> Below there is an add button and search button which can invoke the apis GET /api/recipes/recipes and GET ​/api​/recipes​/searching
---> On the list of recipes a new add to favorite button is there, on click calls the api POST /api/recipes/addrecipe which adds the record to FavoriteRecipes.

####Example Api Request:
GET /api/recipes/recipes - To get all recipes.
--> http://localhost:8080/api/recipes/recipes

GET /api/recipes/favrecipes - To get the favorite recipes of user.
--> http://localhost:8080/api/recipes/favrecipes?userId=112

GET ​/api​/recipes​/searching - To get the filter search result.
--> http://localhost:8080/api/recipes/searching?dishType=non-veg&excludeIngredients=salt&includeIngredients=pepper&includeIngredients=tomato&instructions=cooked&servings=1&userId=112

POST /api/recipes/addrecipe - add a new favorite recipe for user.
--> http://localhost:8080/api/recipes/addrecipe
{
  "recipeId": 1003,
  "servings": 3,
  "userId": 115
}

PUT ​/api​/recipes​/updaterecipe​/{id} - Update the favorite recipe details.
--> http://localhost:8080/api/recipes/updaterecipe/7
{
    "recipeId": 1000,
    "rating": 10,
    "servings": 1,
    "userId": 112
}

DELETE /api/recipes/deleterecipe/{id} - Delete a favorite recipe for a user.
-->http://localhost:8080/api/recipes/deleterecipe/7