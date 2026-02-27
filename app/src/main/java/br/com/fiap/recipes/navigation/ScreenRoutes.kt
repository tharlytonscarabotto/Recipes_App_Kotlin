package br.com.fiap.recipes.navigation

sealed class Destination(val route: String){

    object InitialScreen: Destination("initial")
    object SignupScreen: Destination("signup")
    object ProfileScreen: Destination("profile/{email}"){
        fun createRoute(email: String): String {
            return "profile/$email"
        }
    }

    object HomeScreen: Destination("home/{email}"){
        fun createRoute(email: String): String{
            return "home/$email"
        }
    }

    object LoginScreen: Destination("login")

    object RecipeCategoryScreen: Destination("category/{categoryId}"){
        fun createRoute(categoryId: Int): String{
            return "category/$categoryId"
        }
    }

    object AddRecipeScreen: Destination("AddRecipeScreen")

    object AddRecipeIngredientsScreen : Destination(
        route = "addIngredients/{recipeId}/{recipeName}"
    ){
        fun createRoute(
            recipeId: Int,
            recipeName: String
        ): String {
            return "addIngredients/$recipeId/$recipeName"
        }
    }

    object AddPreparationMethodsScreen : Destination(
        route = "addPreparationMethods/{recipeId}/{recipeName}"
    ){
        fun createRoute(
            recipeId: Int,
            recipeName: String
        ): String {
            return "addPreparationMethods/$recipeId/$recipeName"
        }
    }

    object AddRecipePhotoScreen : Destination(
        route = "AddRecipePhotoScreen/{recipeId}"
    ){
        fun createRoute(
            recipeId: Int
        ): String {
            return "AddRecipePhotoScreen/$recipeId"
        }
    }
}