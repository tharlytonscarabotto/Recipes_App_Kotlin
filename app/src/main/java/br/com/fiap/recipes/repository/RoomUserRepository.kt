package br.com.fiap.recipes.repository

import android.content.Context
import br.com.fiap.recipes.dao.RecipeDataBase
import br.com.fiap.recipes.model.User

class RoomUserRepository(context: Context): UserRepository {

    private val recipeDataBase = RecipeDataBase.getDataBase(context).userDao()

    override fun saveUser(user: User) {
        recipeDataBase.save(user)
    }

    override fun getUser(): User {
        TODO("Not yet implemented")
    }

    override fun getUser(id: Int): User {
        return recipeDataBase.getUserById(1) ?: User()
    }

    override fun getUserByEmail(email: String): User? {
        return recipeDataBase.getUserByEmail(email)
    }

    override fun login(email: String, password: String): Boolean {
        val user = recipeDataBase.login(email, password)
        return user != null
    }

    override fun update(user: User): Int{
        return recipeDataBase.update(user)
    }

    override fun delete(user: User): Int{
        return recipeDataBase.delete(user)
    }
}