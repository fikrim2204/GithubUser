package rpl1pnp.fikri.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val img: Int,
    val follower: String,
    val following: String,
    val company: String,
    val location: String,
    val repository: List<Repository>
) : Parcelable

@Parcelize
data class Repository(
    val title: String,
    val description: String,
    val language: String
) : Parcelable