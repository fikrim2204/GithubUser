package rpl1pnp.fikri.githubuser.repository.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM userFavorite ORDER BY name ASC")
    fun getAllProvider(): Cursor

    @Query("SELECT * FROM userFavorite WHERE id=:id")
    fun getByIdProvider(id: Int?): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProvider(values: UserFavorite?): Long

    @Query("DELETE FROM userFavorite WHERE id=:id")
    fun deleteProvider(id: Int?): Int

    @Query("SELECT * FROM userFavorite ORDER BY name ASC")
    suspend fun getAll(): List<UserFavorite>

    @Query("SELECT * FROM userFavorite WHERE id=:id")
    suspend fun getById(id: Int?): UserFavorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(values: UserFavorite?): Long

    @Query("DELETE FROM userFavorite WHERE id=:id")
    suspend fun delete(id: Int?): Int
}