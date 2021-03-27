package rpl1pnp.fikri.githubuser.repository.local

import kotlinx.coroutines.flow.Flow
import rpl1pnp.fikri.githubuser.repository.local.dao.UserFavoriteDao
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

class DatabaseRepository(private val userFavoriteDao: UserFavoriteDao) {
    val allUserFavorites : Flow<List<UserFavorite>> = userFavoriteDao.getAll()

    suspend fun insert(userFavorite: UserFavorite) {
        userFavoriteDao.insert(userFavorite)
    }

    suspend fun delete(userFavorite: UserFavorite) {
        userFavoriteDao.delete(userFavorite)
    }
}