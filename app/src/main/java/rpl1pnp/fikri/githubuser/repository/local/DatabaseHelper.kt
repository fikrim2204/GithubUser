package rpl1pnp.fikri.githubuser.repository.local

import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

interface DatabaseHelper {
    suspend fun getAllUser():List<UserFavorite>
    suspend fun getById(id: Int?): UserFavorite
    suspend fun insert(user: UserFavorite)
    suspend fun delete(user: UserFavorite)
}