package rpl1pnp.fikri.githubuser.utils

import android.view.View

fun loading(loading: Boolean): Int {
    return if (loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
