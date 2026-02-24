package br.com.fiap.recipes.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import br.com.fiap.recipes.model.Category
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.fiap.recipes.factory.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun getAllCategories(): List<Category>{

    var categories by remember{
        mutableStateOf(listOf<Category>())
    }
    
    val callCategories = RetrofitClient.getCategoryService().getAllCategories()
    
    callCategories.enqueue(object : Callback<List<Category>> {
        override fun onResponse(
            call: Call<List<Category>?>,
            response: Response<List<Category>?>
        ) {
            categories = response.body()!!
        }

        override fun onFailure(
            p0: Call<List<Category>?>,
            p1: Throwable
        ) {
            println(p1.printStackTrace())
        }
    })

    return categories
}


//fun getAllCategories() = listOf<Category>(
//    Category(
//        id = 1,
//        name = "Chicken",
//        image = R.drawable.chicken,
//        background = Color(0xFFABF2E9)
//    ),
//    Category(
//        id = 2,
//        name = "Beef",
//        image = R.drawable.beef,
//        background = Color(0xFFF4D6C0)
//    ),
//    Category(
//        id = 3,
//        name = "Fish",
//        image = R.drawable.fish,
//        background = Color(0xFFC6DAFA)
//    ),
//    Category(
//        id = 4,
//        name = "Bakery",
//        image = R.drawable.bakery,
//        background = Color(0xFFF8D9D9)
//    ),
//    Category(
//        id = 5,
//        name = "Vegetable",
//        image = R.drawable.vegetable,
//        background = Color(0xFFABF2E9)
//    ),
//    Category(
//        id = 6,
//        name = "Dessert",
//        image = R.drawable.dessert,
//        background = Color(0xFF72412B)
//    ),
//    Category(
//        id = 7,
//        name = "Drink",
//        image = R.drawable.drink,
//        background = Color(0xFF80DEEA)
//    )
//)



@Composable
fun getCategoryById(id: Int) = getAllCategories()
    .find { category ->
        category.id == id
    }