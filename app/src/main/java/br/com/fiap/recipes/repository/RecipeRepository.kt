package br.com.fiap.recipes.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.recipes.R
import br.com.fiap.recipes.factory.RetrofitClient
import br.com.fiap.recipes.model.Category
import br.com.fiap.recipes.model.DifficultyLevel
import br.com.fiap.recipes.model.Ingredient
import br.com.fiap.recipes.model.PreparationMethod
import br.com.fiap.recipes.model.Recipe
import br.com.fiap.recipes.model.RecipeRequest
import br.com.fiap.recipes.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.LocalDate

fun getAllRecipes() = listOf<Recipe>(
    Recipe(
        id = 1,
        category = Category(id = 6, name = "Desserts"),
        user = User(id = 1, name = "Ana Maria"),
        difficultyLevel = DifficultyLevel.INTERMEDIATE,
        name = "Carrot Cake",
        description = "Moist, spiced, carrot-filled cake with tangy cream cheese frosting",
        cookingTime = 60,
        createdAt = LocalDate.now(),
        image = "/images/bolo_de_cenoura.jpg"
    ),

    Recipe(
        id = 2,
        category = Category(id = 4, name = "Bakery"),
        user = User(id = 2, name = "Michael Keane"),
        difficultyLevel = DifficultyLevel.ADVANCED,
        name = "Calabrese Bread",
        description = "Spicy sausage and cheese bread: soft, savory, delicious.",
        cookingTime = 40,
        createdAt = LocalDate.now(),
        image = "/images/pao_calabresa.jpeg"
    ),

    Recipe(
        id = 3,
        category = Category(id = 5, name = "Vegetable"),
        user = User(id = 3, name = "Mark Smith"),
        difficultyLevel = DifficultyLevel.BEGINNER,
        name = "Palm Heart Salad",
        description = "Fresh, moist, spiced salad with delicious palm heart",
        cookingTime = 20,
        createdAt = LocalDate.now(),
        image = "/images/salada_de_palmito.png"
    ),

    Recipe(
        id = 4,
        category = Category(id = 2, name = "Beef"),
        user = User(id = 1, name = "Ana Maria"),
        difficultyLevel = DifficultyLevel.ADVANCED,
        name = "Feijoada",
        description = "The best and most unique brazilian dish ever!",
        cookingTime = 120,
        createdAt = LocalDate.now(),
        image = "/images/feijoada.jpg"
    )
)

@Composable
fun getRecipesByCategory(id: Int): List<Recipe>{
    var recipes by remember {
        mutableStateOf(listOf<Recipe>())
    }

    val callRecipesByCategory = RetrofitClient.getRecipeService().getRecipesByCategory(id)

    callRecipesByCategory.enqueue(object : Callback<List<Recipe>> {
        override fun onResponse(
            p0: Call<List<Recipe>?>,
            p1: Response<List<Recipe>?>
        ) {
            recipes = p1.body() ?: emptyList()
        }

        override fun onFailure(
            p0: Call<List<Recipe>?>,
            p1: Throwable
        ) {
            println("ERRO ------> ${p1.printStackTrace()}")
            println(p1.message)
        }
    })

    return recipes
}

@Composable
fun getLatestRecipes(): List<Recipe> {
    var latestRecipes by remember {
        mutableStateOf(listOf<Recipe>())
    }

    val callLatestRecipes = RetrofitClient.getRecipeService().getLatestRecipes()

    callLatestRecipes.enqueue(object : Callback<List<Recipe>> {
        override fun onResponse(
            p0: Call<List<Recipe>?>,
            p1: Response<List<Recipe>?>
        ) {
            latestRecipes = p1.body() ?: emptyList()
        }

        override fun onFailure(
            p0: Call<List<Recipe>?>,
            p1: Throwable
        ) {
            println(p1.message)
        }
    })
    return latestRecipes
}

suspend fun saveRecipe(recipeRequest: RecipeRequest): RecipeRequest{

    val newRecipe = RetrofitClient.getRecipeService().saveRecipe(recipeRequest)
    return newRecipe

}

suspend fun saveRecipeIngredients(
    recipeId: Int,
    ingredients: List<Ingredient>
): List<Ingredient>{
    val newIngredients = RetrofitClient
        .getRecipeService()
        .saveRecipeIngredients(
            recipeId = recipeId,
            ingredients = ingredients
        )
    return newIngredients
}

suspend fun savePreparationMethods(
    recipeId: Int,
    preparationMethods: List<PreparationMethod>
): List<PreparationMethod>{
    val newPreparationMethods = RetrofitClient
        .getRecipeService()
        .savePreparationMethods(
            recipeId = recipeId,
            preparationMethods = preparationMethods
        )
    return newPreparationMethods
}

suspend fun uploadImage(recipeId: Int, file: File){
    val image = MultipartBody.Part
        .createFormData(
            name = "file",
            filename = file.name,
            body = file.asRequestBody()
        )
    RetrofitClient.getRecipeService().uploadImgae(recipeId, image)
}






