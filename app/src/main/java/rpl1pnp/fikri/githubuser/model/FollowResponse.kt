package rpl1pnp.fikri.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class FollowersResponse : ArrayList<DataFollow>()

//class FollowingResponse : ArrayList<DataFollowing>()

@Parcelize
data class DataFollow(
    var avatar_url: String? = null,
    var events_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var gravatar_id: String? = null,
    var html_url: String? = null,
    var id: Int? = null,
    var login: String? = null,
    var node_id: String? = null,
    var organizations_url: String? = null,
    var received_events_url: String? = null,
    var repos_url: String? = null,
    var site_admin: Boolean? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var type: String? = null,
    var url: String? = null
) : Parcelable

//@Parcelize
//data class DataFollowing(
//    var avatar_url: String? = null,
//    var events_url: String? = null,
//    var followers_url: String? = null,
//    var following_url: String? = null,
//    var gists_url: String? = null,
//    var gravatar_id: String? = null,
//    var html_url: String? = null,
//    var id: Int? = null,
//    var login: String? = null,
//    var node_id: String? = null,
//    var organizations_url: String? = null,
//    var received_events_url: String? = null,
//    var repos_url: String? = null,
//    var site_admin: Boolean? = null,
//    var starred_url: String? = null,
//    var subscriptions_url: String? = null,
//    var type: String? = null,
//    var url: String? = null
//) : Parcelable