package rpl1pnp.fikri.githubuser.utils

import android.content.ContentValues
import android.database.Cursor
import android.view.View
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

const val USER_FAV_ID = "id"
const val USER_FAV_NAME = "name"
const val USER_FAV_IMAGE = "image"
const val USER_FAV_USERNAME = "username"
const val USER_FAV_FOLLOWERS = "followers"
const val USER_FAV_FOLLOWING = "following"
const val USER_FAV_LOCATION = "location"
const val USER_FAV_COMPANY = "company"
const val USER_FAV_REPOS = "repos"

fun loading(loading: Boolean): Int {
    return if (loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun viewState(loading: Boolean): Int {
    return if (!loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun ContentValues.toUserFavorite(): UserFavorite =
    UserFavorite(
        id = getAsInteger(USER_FAV_ID),
        name = getAsString(USER_FAV_NAME),
        image = getAsString(USER_FAV_IMAGE),
        username = getAsString(USER_FAV_USERNAME),
        followers = getAsInteger(USER_FAV_FOLLOWERS),
        following = getAsInteger(USER_FAV_FOLLOWING),
        location = getAsString(USER_FAV_LOCATION),
        company = getAsString(USER_FAV_COMPANY),
        repositories = getAsInteger(USER_FAV_REPOS)
    )

fun Cursor.toUserFavorite(): UserFavorite = UserFavorite(
    getInt(getColumnIndexOrThrow(USER_FAV_ID)),
    getString(getColumnIndexOrThrow(USER_FAV_NAME)),
    getString(getColumnIndexOrThrow(USER_FAV_IMAGE)),
    getString(getColumnIndexOrThrow(USER_FAV_USERNAME)),
    getInt(getColumnIndexOrThrow(USER_FAV_FOLLOWERS)),
    getInt(getColumnIndexOrThrow(USER_FAV_FOLLOWING)),
    getString(getColumnIndexOrThrow(USER_FAV_LOCATION)),
    getString(getColumnIndexOrThrow(USER_FAV_COMPANY)),
    getInt(getColumnIndexOrThrow(USER_FAV_REPOS))
)

fun Cursor.toListUserFavorite(): ArrayList<UserFavorite> {
    val listUserFavorite = ArrayList<UserFavorite>()

    apply {
        while (moveToNext()) {
            listUserFavorite.add(toUserFavorite())
        }
    }
    return listUserFavorite
}
