package rpl1pnp.fikri.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.DataUser
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.network.ApiRepo

class DetailViewModel : ViewModel() {
    private val _responseDetail = MutableLiveData<DataUser?>()
    val listResponseDetail: LiveData<DataUser?> = _responseDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _responseFailureDetail = MutableLiveData<ResponseBody?>()
    val listResponseFailureDetail: LiveData<ResponseBody?> = _responseFailureDetail
    private val _isFailedDetail = MutableLiveData<String?>()
    val failedResponseDetail: LiveData<String?> = _isFailedDetail

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(login: String?) {
        _isLoading.value = true
        val client = ApiRepo.getApiService().getUserDetail(login)
        client.enqueue(object : Callback<UserSingleResponse> {
            override fun onResponse(
                call: Call<UserSingleResponse>,
                response: Response<UserSingleResponse>
            ) {
                _isLoading.value = false
                try {
                    _responseDetail.value = response.body()?.items
                } catch (e: Exception) {
                    _responseFailureDetail.value = response.errorBody()
                    Log.e(TAG, "Error : ${e.message}")
                }
            }

            override fun onFailure(call: Call<UserSingleResponse>, t: Throwable) {
                _isLoading.value = false
                _isFailedDetail.value = t.message
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }
}