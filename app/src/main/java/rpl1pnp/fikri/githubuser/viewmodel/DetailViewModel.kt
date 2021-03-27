package rpl1pnp.fikri.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.repository.local.DatabaseHelper
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.repository.network.ApiRepo
import rpl1pnp.fikri.githubuser.repository.network.Constant

class DetailViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val _userFavoriteDb = MutableLiveData<List<UserFavorite>>()
    val listFavoriteUser: LiveData<List<UserFavorite>> = _userFavoriteDb
    private val _responseDetail = MutableLiveData<UserSingleResponse?>()
    val listResponseDetail: LiveData<UserSingleResponse?> = _responseDetail
    private val _responseFollowers = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowers: LiveData<ArrayList<DataFollow>?> = _responseFollowers
    private val _responseFollowing = MutableLiveData<ArrayList<DataFollow>?>()
    val listResponseFollowing: LiveData<ArrayList<DataFollow>?> = _responseFollowing
    private val _responseFailure = MutableLiveData<String>()
    val listResponseFailure: LiveData<String> = _responseFailure
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val mutableSelectedItem = MutableLiveData<String?>()
    val selectedItem: LiveData<String?> get() = mutableSelectedItem

    init {
//        getUserOnDb()
    }

    private fun getUserOnDb() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userFromDb = dbHelper.getUser()
                _userFavoriteDb.value = userFromDb
                _isLoading.value = false
            } catch (e: Exception) {
                _responseFailure.value = "Something Went Wrong"
                _isLoading.value = false
            }
        }
    }

    fun insert(userFavorite: UserFavorite) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                dbHelper.insert(userFavorite)
                _isLoading.value = false
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when trying to insert user"
                _isLoading.value = false
            }
        }
    }

    fun delete(userFavorite: UserFavorite) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                dbHelper.delete(userFavorite)
                _isLoading.value = false
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when trying to delete user"
                _isLoading.value = false
            }
        }
    }

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
                } else {
                    val errors = response.errorBody()!!.string()
                    val jsonObject = JSONObject(errors)
                    _responseFailure.value = jsonObject.getString("message")
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
                } else {
                    val errors = response.errorBody()!!.string()
                    val jsonObject = JSONObject(errors)
                    _responseFailure.value = jsonObject.getString("message")
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
                } else {
                    val errors = response.errorBody()!!.string()
                    val jsonObject = JSONObject(errors)
                    _responseFailure.value = jsonObject.getString("message")
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