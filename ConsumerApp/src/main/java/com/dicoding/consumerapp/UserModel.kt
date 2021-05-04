package com.dicoding.consumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val username: String? = null,
    val avatar: String? = null,
    val id: Int? = null,
) : Parcelable
