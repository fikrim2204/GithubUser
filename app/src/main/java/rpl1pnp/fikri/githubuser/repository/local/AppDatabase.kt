package rpl1pnp.fikri.githubuser.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.githubuser.repository.local.dao.UserFavoriteDao
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.utils.DateTypeConverter

@Database(entities = arrayOf(UserFavorite::class), version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    private class UserFavoriteDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var userFavoritesDao = database.userFavoriteDao()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favoriteuser.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

