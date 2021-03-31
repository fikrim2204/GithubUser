package rpl1pnp.fikri.githubuser.repository.local

import android.database.Cursor
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

interface DatabaseHelper {
    fun getAllUserProvider(): Cursor
    fun getByIdProvider(id: Int?): Cursor
    fun insertProvider(user: UserFavorite?): Long
    fun deleteProvider(id: Int?): Int
    suspend fun getAllUser(): List<UserFavorite>
    suspend fun getById(id: Int?): UserFavorite
    suspend fun insert(user: UserFavorite?) : Long
    suspend fun delete(id: Int?) : Int
}