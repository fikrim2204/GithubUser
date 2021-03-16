package rpl1pnp.fikri.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val avatar: String,
    val follower: Int,
    val following: Int,
    val company: String,
    val location: String,
    val repository: Int
) : Parcelable