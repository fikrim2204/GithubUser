package rpl1pnp.fikri.githubuser.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.UserResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse

interface ApiService {
    @GET(Constant.SEARCH + Constant.USER)
    fun getUser(
        @Header("Authorization") token: String?,
        @Query("q") q: String?,
    ): Call<UserResponse>

    @GET(Constant.USER + "/{login}")
    fun getUserDetail(
        @Header("Authorization") token: String?,
        @Path("login") login: String?
    ): Call<UserSingleResponse>

    @GET(Constant.USER + "/{login}" + Constant.FOLLOWERS)
    fun getFollowers(
        @Header("Authorization") token: String?,
        @Path("login") login: String?
    ): Call<FollowersResponse>

    @GET(Constant.USER + "/{login}" + Constant.FOLLOWING)
    fun getFollowing(
        @Header("Authorization") token: String?,
        @Path("login") login: String?
    ): Call<FollowersResponse>
}