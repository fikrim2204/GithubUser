package rpl1pnp.fikri.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.UserResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.network.ApiRepo
import rpl1pnp.fikri.githubuser.network.Constant

class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<List<UserSingleResponse>?>()
    val listResponse: LiveData<List<UserSingleResponse>?> = _response
    private val _responseFailure = MutableLiveData<ResponseBody?>()
    val listResponseFailure: LiveData<ResponseBody?> = _responseFailure
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isFailed = MutableLiveData<String?>()
    val failedResponse: LiveData<String?> = _isFailed

    fun getUser(query: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getUser(Constant.AUTHORIZATION, query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _response.value = null
                if (response.isSuccessful) {
                    _response.value = response.body()?.items
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isFailed.value = t.message
            }

        })
    }
}