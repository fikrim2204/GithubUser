package rpl1pnp.fikri.githubuser.repository.local

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserFavoritesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getInstance(this, applicationScope) }
    val repository by lazy { DatabaseRepository(database.userFavoriteDao()) }
}