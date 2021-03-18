package rpl1pnp.fikri.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserResponse(
    val incomplete_results: Boolean? = null,
    val items: List<DataUser>? = null,
    val total_count: Int? = null
)

data class UserSingleResponse(
    val incomplete_results: Boolean? = null,
    val items: DataUser? = null,
    val total_count: Int? = null
)

@Parcelize
data class DataUser(
    var avatar_url: String? = null,
    var bio: String? = null,
    var blog: String? = null,
    var company: String? = null,
    var created_at: String? = null,
    var email: String? = null,
    var events_url: String? = null,
    var followers: Int? = null,
    var followers_url: String? = null,
    var following: Int? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var gravatar_id: String? = null,
    var hireable: String? = null,
    var html_url: String? = null,
    var id: Int? = null,
    var location: String? = null,
    var login: String? = null,
    var name: String? = null,
    var node_id: String? = null,
    var organizations_url: String? = null,
    var public_gists: Int? = null,
    var public_repos: Int? = null,
    var received_events_url: String? = null,
    var repos_url: String? = null,
    var score: Double? = null,
    var site_admin: Boolean? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var twitter_username: String? = null,
    var type: String? = null,
    var updated_at: String? = null,
    var url: String? = null
) : Parcelable