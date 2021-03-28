package rpl1pnp.fikri.githubuser.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM userFavorite")
    suspend fun getAll(): List<UserFavorite>

    @Query("SELECT * FROM userFavorite WHERE id=:id")
    suspend fun getById(id: Int?): UserFavorite

    @Insert
    suspend fun insert(vararg userFavorite: UserFavorite)

    @Delete
    suspend fun delete(userFavorite: UserFavorite)
}