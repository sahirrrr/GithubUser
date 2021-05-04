package com.dicoding.githubuser.network

import com.dicoding.githubuser.model.ResponseUser
import com.dicoding.githubuser.model.UserDetailModel
import com.dicoding.githubuser.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // For MainActivity RecyclerView
    @GET("users")
    @Headers("Authorization: token 006268b2531ca25b124586d74d6ddcec46a57e17")
    fun getListUsers(): Call<ArrayList<UserModel>>

    // For Search User
    @GET("search/users")
    @Headers("Authorization: token 006268b2531ca25b124586d74d6ddcec46a57e17")
    fun searchUsers(@Query("q") username:String): Call<ResponseUser>

    // For Detail User
    @GET("users/{username}")
    @Headers("Authorization: token 006268b2531ca25b124586d74d6ddcec46a57e17")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailModel>

    // For Detail User Follower Fragment
    @GET("users/{username}/followers")
    @Headers("Authorization: token 006268b2531ca25b124586d74d6ddcec46a57e17")
    fun getListFollower(@Path("username") username: String?): Call<ArrayList<UserModel>>

    // For Detail User Following Fragment
    @GET("users/{username}/following")
    @Headers("Authorization: token 006268b2531ca25b124586d74d6ddcec46a57e17")
    fun getListFollowing(@Path("username") username: String?): Call<ArrayList<UserModel>>
}