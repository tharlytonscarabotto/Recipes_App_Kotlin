package br.com.fiap.recipes.model

data class RecipeRequest(
    val id: Int? = null,
    val title: String = "",
    val difficultLevel: DifficultyLevel = DifficultyLevel.BEGINNER,
    val description: String = "",
    val cookingTime: Int = 0,
    val creationDate: String = "",
    val category: Category = Category(id = 1)
)
