package rpl1pnp.fikri.githubuser.repository.local.helper

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.repository.local.provider.UserFavoriteProvider
import rpl1pnp.fikri.githubuser.utils.toContentValues
import rpl1pnp.fikri.githubuser.utils.toListUserFavorite
import rpl1pnp.fikri.githubuser.utils.toUserFavorite

class UserFavoriteHelper() {

    companion object {
        val CONTENT_URI = Uri.parse("content://${UserFavoriteProvider.AUTHORITY}/userFavorite")
    }

    suspend fun getUser(context: Context): LiveData<List<UserFavorite>?> {
        val listUserFavorite = MutableLiveData<List<UserFavorite>?>()

        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            listUserFavorite.value = it.toListUserFavorite()
        }
        cursor?.close()
        return listUserFavorite
    }

    suspend fun getUserById(id: Int?, context: Context): LiveData<UserFavorite> {
        val userFavorite = MutableLiveData<UserFavorite>()
        val cursor =
            context.contentResolver.query("$CONTENT_URI/$id".toUri(), null, null, null, null)
        cursor?.let {
            userFavorite.value = it.toUserFavorite()
        }
        cursor?.close()
        return userFavorite
    }

    suspend fun insertFavorite(userFavorite: UserFavorite, context: Context) {
        context.contentResolver.insert(CONTENT_URI, userFavorite.toContentValues())
    }

    suspend fun deleteFavorite(id: Int?, context: Context) {
        context.contentResolver.delete("$CONTENT_URI/$id".toUri(), null, null)
    }
}