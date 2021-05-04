package com.dicoding.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.database.AppDatabase
import com.dicoding.githubuser.database.UserFavoriteDao
import com.dicoding.githubuser.database.UserFavoriteEntity
import com.dicoding.githubuser.model.UserDetailModel
import com.dicoding.githubuser.model.UserModel
import com.dicoding.githubuser.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val listUser = MutableLiveData<UserDetailModel>()
    private val listUserDetail = MutableLiveData<ArrayList<UserModel>>()
    private val TAG = DetailViewModel::class.java.simpleName

    fun getUser(): LiveData<UserDetailModel> = listUser
    fun getFollowerUser(): LiveData<ArrayList<UserModel>> = listUserDetail

    private var favDao: UserFavoriteDao?
    private var database: AppDatabase? = AppDatabase.getDatabase(application)

    init {
        favDao = database?.getUserFavoriteDao()
    }

    fun userDetail(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object : Callback<UserDetailModel> {
            override fun onResponse(call: Call<UserDetailModel>, response: Response<UserDetailModel>) {
                if (response.isSuccessful) {
                    val itemList = response.body()
                    listUser.postValue(itemList)
                }
            }

            override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                Log.d(TAG, "Something unexpected happened to our request")
                t.printStackTrace()
            }
        })
    }

    fun getFollowing(username: String?) {
        val client = ApiConfig.getApiService().getListFollowing(username)

        client.enqueue(object: Callback<ArrayList<UserModel>> {
            override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()
                    if (dataArray != null) {
                        listUserDetail.postValue(dataArray)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.d(TAG, "Something unexpected happened to our request")
                t.printStackTrace()
            }
        })
    }

    fun getFollower(username: String?) {
        val client = ApiConfig.getApiService().getListFollower(username)

        client.enqueue(object: Callback<ArrayList<UserModel>> {
            override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()
                    if (dataArray != null) {
                        listUserDetail.postValue(dataArray)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.d(TAG, "Something unexpected happened to our request")
                t.printStackTrace()
            }
        })
    }

    fun addToFavorite(username: String, avatar: String?, id: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavoriteEntity(username, avatar, id)
            favDao?.insert(user)
        }
    }

    suspend fun checkFavorite(id: Int?) = favDao?.check(id)

    fun deleteFromFavorite(id: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.delete(id)
        }
    }
}