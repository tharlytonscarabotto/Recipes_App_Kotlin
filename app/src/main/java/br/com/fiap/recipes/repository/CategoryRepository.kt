package br.com.fiap.recipes.repository

import androidx.compose.ui.graphics.Color
import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.Category

fun getAllCategories() = listOf<Category>(
    Category(
        id = 1,
        name = "Chicken",
        image = R.drawable.chicken,
        background = Color(0xFFABF2E9)
    ),
    Category(
        id = 2,
        name = "Beef",
        image = R.drawable.beef,
        background = Color(0xFFF4D6C0)
    ),
    Category(
        id = 3,
        name = "Fish",
        image = R.drawable.fish,
        background = Color(0xFFC6DAFA)
    ),
    Category(
        id = 4,
        name = "Bakery",
        image = R.drawable.bakery,
        background = Color(0xFFF8D9D9)
    ),
    Category(
        id = 5,
        name = "Vegetable",
        image = R.drawable.vegetable,
        background = Color(0xFFABF2E9)
    ),
    Category(
        id = 6,
        name = "Dessert",
        image = R.drawable.dessert,
        background = Color(0xFF72412B)
    ),
    Category(
        id = 7,
        name = "Drink",
        image = R.drawable.drink,
        background = Color(0xFF80DEEA)
    )
)