package br.com.fiap.recipes.repository

import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.Category
import br.com.fiap.recipes.model.DifficultyLevel
import br.com.fiap.recipes.model.Recipe
import br.com.fiap.recipes.model.User
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
        image = R.drawable.bolo_cenoura
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
        image = R.drawable.pao_calabresa
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
        image = R.drawable.salada_de_palmito
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
        image = R.drawable.feijoada
    )
)

fun getRecipesByCategory(id: Int) = getAllRecipes()
    .filter { recipe ->
        recipe.category.id == id
    }