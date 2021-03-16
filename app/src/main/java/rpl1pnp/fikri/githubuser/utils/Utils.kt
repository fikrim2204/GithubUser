package rpl1pnp.fikri.githubuser.utils

import android.content.Context
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    return try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun getId(context: Context, avatar: String): Int {
    return context.resources.getIdentifier(
        avatar,
        "drawable",
        context.packageName
    )
}
