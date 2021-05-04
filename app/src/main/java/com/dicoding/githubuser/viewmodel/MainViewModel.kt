package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.model.ResponseUser
import com.dicoding.githubuser.model.UserModel
import com.dicoding.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<UserModel>>()
    private val TAG = MainViewModel::class.java.simpleName

    fun getUser(): LiveData<ArrayList<UserModel>> = listUser

    fun setUser() {
        val client = ApiConfig.getApiService().getListUsers()

        client.enqueue(object : Callback<ArrayList<UserModel>> {
            override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                if (response.isSuccessful) {
                    val itemList = response.body()
                    listUser.postValue(itemList)
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.d(TAG, "Something unexpected happened to our request")
                t.printStackTrace()
            }
        })
    }

    fun setSearchUser(query: String) {
        val client = ApiConfig.getApiService().searchUsers(query)

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.items as ArrayList<UserModel>
                    listUser.postValue(dataArray)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d(TAG, "Something unexpected happened to our request")
                t.printStackTrace()
            }
        })

    }
}