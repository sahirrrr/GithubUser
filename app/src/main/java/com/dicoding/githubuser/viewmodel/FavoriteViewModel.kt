package com.dicoding.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.AppDatabase
import com.dicoding.githubuser.database.UserFavoriteDao
import com.dicoding.githubuser.database.UserFavoriteEntity

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var favDao: UserFavoriteDao? = null
    private var database: AppDatabase? = null

    init {
        database = AppDatabase.getDatabase(application)
        favDao = database?.getUserFavoriteDao()
    }

    fun getFavorite(): LiveData<List<UserFavoriteEntity>>? = favDao?.getAll()
}