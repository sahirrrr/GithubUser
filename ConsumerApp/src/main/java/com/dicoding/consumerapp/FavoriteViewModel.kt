package com.dicoding.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteList = MutableLiveData<ArrayList<UserModel>>()

    fun setFavorite(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val list = MappingHelper.mapCursorToArrayList(cursor)
        favoriteList.postValue(list)
    }

    fun getFavorite(): LiveData<ArrayList<UserModel>> = favoriteList
}