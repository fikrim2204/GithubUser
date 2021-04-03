package rpl1pnp.fikri.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.UserResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.repository.network.ApiRepo
import rpl1pnp.fikri.githubuser.repository.network.Constant

class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<List<UserSingleResponse>?>()
    val listResponse: LiveData<List<UserSingleResponse>?> = _response
    private val _responseFailure = MutableLiveData<String>()
    val listResponseFailure: LiveData<String> = _responseFailure
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _failed = MutableLiveData<String?>()
    val failedResponse: LiveData<String?> = _failed

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
                } else {
                    val errors = response.errorBody()!!.string()
                    val jsonObject = JSONObject(errors)
                    _responseFailure.value = jsonObject.getString("message")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _failed.value = t.message
            }
        })
    }
}