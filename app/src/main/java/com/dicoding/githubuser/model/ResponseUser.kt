package com.dicoding.githubuser.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(
	@field:SerializedName("items")
	val items: ArrayList<UserModel>? = null
)
