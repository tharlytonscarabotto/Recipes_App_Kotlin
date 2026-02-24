package br.com.fiap.recipes.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoryId") val id: Int = 0,
    @SerializedName("categoryName") val name: String = "",
    @SerializedName("url") val image: String = "",
    @SerializedName("color") val background: String = "FFFFFFFF"
    )
