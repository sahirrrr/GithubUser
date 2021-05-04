package com.dicoding.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<UserModel> {
        val userList = ArrayList<UserModel>()
        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                userList.add(UserModel(username, avatar, id))
            }
        }
        return userList
    }
}