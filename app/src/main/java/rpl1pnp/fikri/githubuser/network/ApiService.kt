package rpl1pnp.fikri.githubuser.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.FollowingResponse
import rpl1pnp.fikri.githubuser.model.UserResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse

interface ApiService {
    @GET(Constant.SEARCH + Constant.USER)
    fun getUser(
        @Query("q") q: String?
    ): Call<UserResponse>

    @GET(Constant.USER + "/{login}")
    fun getUserDetail(
        @Path("login") login: String?
    ): Call<UserSingleResponse>

    @GET(Constant.USER+"/login"+Constant.FOLLOWERS)
    fun getFollowers(
        @Path("login") login: String?
    ): Call<FollowersResponse>

    @GET(Constant.USER+"/login"+Constant.FOLLOWING)
    fun getFollowing(
        @Path("login") login: String?
    ): Call<FollowingResponse>
}