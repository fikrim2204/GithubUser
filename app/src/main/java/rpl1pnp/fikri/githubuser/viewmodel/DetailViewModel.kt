package rpl1pnp.fikri.githubuser.viewmodel

import android.util.Log
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
import rpl1pnp.fikri.githubuser.repository.local.helper.DatabaseHelperImpl
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.repository.network.ApiRepo
import rpl1pnp.fikri.githubuser.repository.network.Constant

class DetailViewModel(private val dbHelper: DatabaseHelperImpl) : ViewModel() {
    private val _searchUserbyId = MutableLiveData<UserFavorite>()
    val userSearchById: LiveData<UserFavorite> = _searchUserbyId
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
    private val _failed = MutableLiveData<String?>()
    val failedResponse: LiveData<String?> = _failed

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val userFromDb = dbHelper.getAllUser()
                _userFavoriteDb.value = userFromDb
            } catch (e: Exception) {
                _isLoading.value = false
                _responseFailure.value = "Something went wrong when get user"
                Log.d("TAG", "${e.message}")
            }
        }
    }

//    : LiveData<List<UserFavorite>> {
//        return userFavoriteHelper.getUser(context)
//    }
//    {
//        try {
//            _userFavoriteDb.value = userFavoriteHelper.getUser(context)
//        } catch (e: Exception) {
//            _isLoading.value = false
//            _responseFailure.value = "Something went wrong when get user"
//            Log.d("TAG", "${e.message}")
//        }

//        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
//        cursor?.let {
//            _userFavoriteDb.value = it.toListUserFavorite()
//            it.close()
//        }

//
//    }

    fun getUserById(id: Int?) {
        viewModelScope.launch {
            try {
                val search = dbHelper.getById(id)
                _searchUserbyId.value = search
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when searching user"
            }
        }
    }
//    {
//        try {
//            _searchUserbyId.value =

//            val uri = "$CONTENT_URI/$id".toUri()
//            val cursor = context.contentResolver.query(uri, null, null, null, null)
//            cursor?.let {
//                it.toUserFavorite()
//                it.close()
//            }
//        } catch (e: Exception) {
//            _responseFailure.value = "Something went wrong when searching user"
//        }

//        try {
//            val search = dbHelper.getByIdProvider(id)
//            _searchUserbyId.value = search.toUserFavorite()
//        } catch (e: Exception) {
//            _responseFailure.value = "Something went wrong when searching user"
//        }
//    }

    fun insert(userFavorite: UserFavorite) {
        viewModelScope.launch {
            try {
                dbHelper.insert(userFavorite)
            } catch (e: Exception) {
                _responseFailure.value = "Something went wrong when trying to insert user"
                Log.d("TAG", "${e.message}")
            }
        }
    }

//        userFavoriteHelper.insertFavorite(userFavorite, context)
//    {
//        try {
////            val cursor = context.contentResolver.insert(CONTENT_URI, userFavorite.toContentValues())
////            dbHelper.insertProvider(cursor)
//        } catch (e: Exception) {
//            _responseFailure.value = "Something went wrong when trying to insert user"
//            Log.d("TAG", "${e.message}")
//        }
//    }

    fun delete(id: Int?) {
        viewModelScope.launch {
            try {
                dbHelper.delete(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    = userFavoriteHelper.deleteFavorite(id, context)
//    {
//        try {
////            val uri = "$CONTENT_URI/$id".toUri()
////            val cursor = context.contentResolver.delete(uri, null, null)
////            dbHelper.deleteProvider(cursor)
//        } catch (e: Exception) {
//            _responseFailure.value = "Something went wrong when trying to delete user"
//            Log.d("TAG", "${e.message}")
//        }
//    }

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
                _failed.value = t.message
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