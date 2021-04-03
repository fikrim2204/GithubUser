package rpl1pnp.fikri.githubuser.repository.local.helper

import android.database.Cursor
import rpl1pnp.fikri.githubuser.repository.local.AppDatabase
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override fun getAllUserProvider(): Cursor = appDatabase.userFavoriteDao().getAllProvider()
    override fun getByIdProvider(id: Int?): Cursor = appDatabase.userFavoriteDao().getByIdProvider(id)
    override fun insertProvider(user: UserFavorite?): Long = appDatabase.userFavoriteDao().insertProvider(user)
    override fun deleteProvider(id: Int?): Int = appDatabase.userFavoriteDao().deleteProvider(id)

    override suspend fun getAllUser(): List<UserFavorite> = appDatabase.userFavoriteDao().getAll()
    override suspend fun getById(id: Int?): UserFavorite = appDatabase.userFavoriteDao().getById(id)
    override suspend fun insert(user: UserFavorite?): Long = appDatabase.userFavoriteDao().insert(user)
    override suspend fun delete(id: Int?): Int = appDatabase.userFavoriteDao().delete(id)
}