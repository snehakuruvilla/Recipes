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
