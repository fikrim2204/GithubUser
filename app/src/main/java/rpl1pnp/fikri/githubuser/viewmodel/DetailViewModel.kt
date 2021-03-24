package rpl1pnp.fikri.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.network.ApiRepo
import rpl1pnp.fikri.githubuser.network.Constant

class DetailViewModel : ViewModel() {
    private val _responseDetail = MutableLiveData<UserSingleResponse?>()
    val listResponseDetail: LiveData<UserSingleResponse?> = _responseDetail
    private val _responseFollowers = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowers: LiveData<ArrayList<DataFollow>?> = _responseFollowers
    private val _responseFollowing = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowing: LiveData<ArrayList<DataFollow>?> = _responseFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val mutableSelectedItem = MutableLiveData<String?>()
    val selectedItem: LiveData<String?> get() = mutableSelectedItem

    fun getUserDetail(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getUserDetail(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<UserSingleResponse> {
            override fun onResponse(
                call: Call<UserSingleResponse>,
                response: Response<UserSingleResponse>
            ) {
                if (response.isSuccessful) {
                    _responseDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserSingleResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getFollowers(login: String?) {
        val client = ApiRepo.getApiService().getFollowers(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                if (response.isSuccessful) {
                    _responseFollowers.value = response.body()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<FollowersResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowing(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getFollowing(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                if (response.isSuccessful) {
                    _responseFollowing.value = response.body()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<FollowersResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun selectItem(item: String?) {
        mutableSelectedItem.value = item
    }

}