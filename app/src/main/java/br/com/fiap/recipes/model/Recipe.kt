package br.com.fiap.recipes.model

import androidx.annotation.DrawableRes
import br.com.fiap.recipes.R
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Recipe(
    val id: Int = 0,
    val category: Category,
    val user: User,
    @SerializedName("difficultLevel") val difficultyLevel: DifficultyLevel,
    @SerializedName("title") val name: String = "",
    val description: String = "",
    val cookingTime: Int = 0,
    val createdAt: LocalDate = LocalDate.now(),
    @SerializedName("url") val image: String = ""
)
