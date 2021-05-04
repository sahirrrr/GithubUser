package com.dicoding.githubuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class UserFavoriteEntity(
    val username: String,
    val avatar: String?,
    @PrimaryKey val id: Int?,
): Serializable
