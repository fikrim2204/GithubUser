package rpl1pnp.fikri.githubuser.repository.local

import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

interface DatabaseHelper {
    suspend fun getUser(): List<UserFavorite>
    suspend fun insert(user: UserFavorite)
    suspend fun delete(user: UserFavorite)
}