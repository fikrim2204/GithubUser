package rpl1pnp.fikri.githubuser.viewmodel

import android.util.Log
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
    private val _responseDetail = MutableLiveData<UserSingleResponse>()
    val listResponseDetail: LiveData<UserSingleResponse> = _responseDetail
    private val _responseFollowers = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowers: LiveData<ArrayList<DataFollow>?> = _responseFollowers
    private val _responseFollowing = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowing: LiveData<ArrayList<DataFollow>?> = _responseFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _codeError = MutableLiveData<String?>()
    val codeError: LiveData<String?> = _codeError
    private val mutableSelectedItem = MutableLiveData<String?>()
    val selectedItem: LiveData<String?> get() = mutableSelectedItem

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getUserDetail(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<UserSingleResponse> {
            override fun onResponse(
                call: Call<UserSingleResponse>,
                response: Response<UserSingleResponse>
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

            override fun onFailure(call: Call<UserSingleResponse>, t: Throwable) {
                _isLoading.value = false
                _codeError.value = t.message
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    fun getFollowers(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getFollowers(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                _isLoading.value = false
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
        val client = ApiRepo.getApiService().getFollowing(Constant.AUTHORIZATION, login)
        client.enqueue(object : Callback<FollowersResponse> {
            override fun onResponse(
                call: Call<FollowersResponse>,
                response: Response<FollowersResponse>
            ) {
                try {
                    _isLoading.value = false
                    _responseFollowing.value = response.body()
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

    fun selectItem(item: String?) {
        mutableSelectedItem.value = item
    }

}