package rpl1pnp.fikri.githubuser.repository.local

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rpl1pnp.fikri.githubuser.provider.UserFavoriteProvider
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.utils.toContentValues
import rpl1pnp.fikri.githubuser.utils.toListUserFavorite
import rpl1pnp.fikri.githubuser.utils.toUserFavorite

class UserFavoriteHelper() {

    companion object {
        val CONTENT_URI = Uri.parse("content://${UserFavoriteProvider.AUTHORITY}/userFavorite")
    }

    fun getUser(context: Context): LiveData<List<UserFavorite>?> {
        val listUserFavorite = MutableLiveData<List<UserFavorite>?>()

        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            listUserFavorite.value = it.toListUserFavorite()
            it.close()
        }
        return listUserFavorite
    }

    fun getUserById(id: Int, context: Context): LiveData<UserFavorite> {
        val userFavorite = MutableLiveData<UserFavorite>()

        val cursor =
            context.contentResolver.query("$CONTENT_URI/$id".toUri(), null, null, null, null)
        cursor?.let {
            userFavorite.value = it.toUserFavorite()
            it.close()
        }
        return userFavorite
    }

    fun insertFavorite(userFavorite: UserFavorite, context: Context): LiveData<Long> {
        val userFavoriteLive = MutableLiveData<Long>()

        val cursor = context.contentResolver.insert(CONTENT_URI, userFavorite.toContentValues())
        cursor?.let {
            userFavoriteLive.value = 1
        }
        return userFavoriteLive
    }

    fun deleteFavorite(id: Int, context: Context): LiveData<Int> {
        val deletedUser = MutableLiveData<Int>()

        val cursor = context.contentResolver.delete("$CONTENT_URI/$id".toUri(), null, null)
        cursor.let { deletedUser.value = cursor }
        return deletedUser
    }
}