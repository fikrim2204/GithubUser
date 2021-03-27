package rpl1pnp.fikri.githubuser.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM userFavorite")
    fun getAll(): List<UserFavorite>

    @Insert
    fun insert(vararg userFavorite: UserFavorite)

    @Delete
    fun delete(userFavorite: UserFavorite)
}