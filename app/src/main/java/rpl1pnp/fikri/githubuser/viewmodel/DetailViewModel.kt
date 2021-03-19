package rpl1pnp.fikri.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.DataUser
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.FollowingResponse
import rpl1pnp.fikri.githubuser.network.ApiRepo

class DetailViewModel : ViewModel() {
    private val _responseDetail = MutableLiveData<DataUser?>()
    val listResponseDetail: LiveData<DataUser?> = _responseDetail
    private val _responseFollowers = MutableLiveData<FollowersResponse?>()
    val listResponseFollowers: LiveData<FollowersResponse?> = _responseFollowers
    private val _responseFollowing = MutableLiveData<FollowingResponse?>()
    val listResponseFollowing: LiveData<FollowingResponse?> = _responseFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _codeError = MutableLiveData<String?>()
    val codeError: LiveData<String?> = _codeError

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getUserDetail(login)
        client.enqueue(object : Callback<DataUser> {
            override fun onResponse(
                call: Call<DataUser>,
                response: Response<DataUser>
            ) {
                _isLoading.value = false
                try {
                    _responseDetail.value = response.body()
                    Log.d(TAG, "ResponseBody : ${_responseDetail.value}")
                } catch (e: Exception) {
                    _codeError.value = response.code().toString()
                    Log.e(TAG, "Error : ${e.message}")
                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                _isLoading.value = false
                _codeError.value = t.message
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    fun getFollowers(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getFollowers(login)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                try {
                    _responseFollowers.value = response.body()
                } catch (e: Exception) {
                    _codeError.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<FollowersResponse>, t: Throwable) {
                _isLoading.value = false
                _codeError.value = t.message
            }
        })
    }

    fun getFollowing(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getFollowing(login)
        client.enqueue(object : Callback<FollowingResponse> {
            override fun onResponse(
                call: Call<FollowingResponse>,
                response: Response<FollowingResponse>
            ) {
                try {
                    _responseFollowing.value = response.body()
                } catch (e: Exception) {
                    _codeError.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<FollowingResponse>, t: Throwable) {
                _isLoading.value = false
                _codeError.value = t.message
            }
        })
    }
}