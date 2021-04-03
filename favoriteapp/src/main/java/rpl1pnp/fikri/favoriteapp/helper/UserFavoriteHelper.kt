package rpl1pnp.fikri.favoriteapp.helper

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rpl1pnp.fikri.favoriteapp.entity.UserFavorite
import rpl1pnp.fikri.favoriteapp.utils.toContentValues
import rpl1pnp.fikri.favoriteapp.utils.toListUserFavorite
import rpl1pnp.fikri.favoriteapp.utils.toUserFavorite

class UserFavoriteHelper(private val context: Context) {

    companion object {
        const val AUTHORITY = "rpl1pnp.fikri.githubuser"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/userFavorite")
    }

    fun getUser(): LiveData<List<UserFavorite>?> {
        val listUserFavorite = MutableLiveData<List<UserFavorite>?>()

        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            listUserFavorite.value = it.toListUserFavorite()
        }
        cursor?.close()
        return listUserFavorite
    }

    fun getUserById(id: Int): LiveData<UserFavorite> {
        val userFavorite = MutableLiveData<UserFavorite>()
        val cursor =
            context.contentResolver.query("$CONTENT_URI/$id".toUri(), null, null, null, null)
        cursor?.let {
            if (it.count > 0) {
                while (it.moveToNext()) {
                    userFavorite.value = it.toUserFavorite()
                }
            }
        }
        cursor?.close()
        return userFavorite
    }

    fun insertFavorite(userFavorite: UserFavorite) {
        context.contentResolver.insert(CONTENT_URI, userFavorite.toContentValues())
    }

    fun deleteFavorite(id: Int) {
        context.contentResolver.delete("$CONTENT_URI/$id".toUri(), null, null)
    }
}