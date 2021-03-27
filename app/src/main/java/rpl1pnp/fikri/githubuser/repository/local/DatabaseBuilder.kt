package rpl1pnp.fikri.githubuser.repository.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favoriteuser.db"
                ).build()
            }
        }
        return INSTANCE
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}