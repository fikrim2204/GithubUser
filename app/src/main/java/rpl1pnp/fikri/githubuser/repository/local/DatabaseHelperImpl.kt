package rpl1pnp.fikri.githubuser.repository.local

import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getAllUser(): List<UserFavorite> = appDatabase.userFavoriteDao().getAll()
    override suspend fun getById(id: Int?): UserFavorite = appDatabase.userFavoriteDao().getById(id)

    override suspend fun insert(user: UserFavorite) = appDatabase.userFavoriteDao().insert(user)

    override suspend fun delete(user: UserFavorite) = appDatabase.userFavoriteDao().delete(user)
}