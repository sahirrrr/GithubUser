package com.dicoding.githubuser.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): LiveData<List<UserFavoriteEntity>>

    @Query("SELECT * FROM favorite")
    fun getCursor(): Cursor

    @Query("SELECT count(*) FROM favorite WHERE id = :id")
    suspend fun check(id: Int?) : Int

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun delete(id: Int?) : Int

    @Insert
    suspend fun insert(users: UserFavoriteEntity)

}