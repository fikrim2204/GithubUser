package rpl1pnp.fikri.githubuser.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.model.FollowersResponse
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.repository.local.DatabaseRepository
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.repository.network.ApiRepo
import rpl1pnp.fikri.githubuser.repository.network.Constant

class DetailViewModel(private val repository: DatabaseRepository) : ViewModel() {
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

    val listUserFavorites: LiveData<List<UserFavorite>> = repository.allUserFavorites.asLiveData()

    fun insert(userFavorite: UserFavorite) {
        viewModelScope.launch {
            try {
                repository.insert(userFavorite)
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when trying to insert user"
            }
        }
    }

    fun delete(userFavorite: UserFavorite) {
        viewModelScope.launch {
            try {
                repository.delete(userFavorite)
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when trying to delete user"
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