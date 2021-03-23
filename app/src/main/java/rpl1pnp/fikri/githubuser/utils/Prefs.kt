package rpl1pnp.fikri.githubuser.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val APP_PREF = "application_preference"
        private const val DARK_MODE = "dark_mode"
    }

    var darkModePref: Boolean
        get() = preferences.getBoolean(DARK_MODE, false)
        set(value) = preferences.edit().putBoolean(DARK_MODE, value).apply()
}