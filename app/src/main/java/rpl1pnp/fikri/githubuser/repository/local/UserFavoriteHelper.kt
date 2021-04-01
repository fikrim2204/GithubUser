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

    fun getUser(context: Context): List<UserFavorite> {
        var listUserFavorite: List<UserFavorite> = mutableListOf()

        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            listUserFavorite = it.toListUserFavorite()
        }
        cursor?.close()
        return listUserFavorite
    }

    fun getUserById(id: Int?, context: Context): UserFavorite {
        var userFavorite = UserFavorite(null, null, null, null, null, null, null, null, null)
        val cursor =
            context.contentResolver.query("$CONTENT_URI/$id".toUri(), null, null, null, null)
        cursor?.let {
            userFavorite = it.toUserFavorite()
        }
        cursor?.close()
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

    fun deleteFavorite(id: Int?, context: Context){
        context.contentResolver.delete("$CONTENT_URI/$id".toUri(), null, null)
    }
}