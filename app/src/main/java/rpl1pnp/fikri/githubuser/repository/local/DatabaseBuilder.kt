package rpl1pnp.fikri.githubuser.repository.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
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

    fun destroyInstance() {
        INSTANCE = null
    }
}