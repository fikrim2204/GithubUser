package rpl1pnp.fikri.githubuser.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rpl1pnp.fikri.githubuser.repository.local.dao.UserFavoriteDao
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.utils.DateTypeConverter

@Database(entities = [UserFavorite::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao
}

