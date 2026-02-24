package br.com.fiap.recipes.service

import br.com.fiap.recipes.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    //Busca a lista de categorias
    @GET("categories")
    fun getAllCategories(): Call<List<Category>>

    //Busca uma unica categoria por ID
    @GET("categories/{categoryId}")
    fun getCategoryById(@Path("categoryId") categoryId: Int): Call<Category>

}