package com.dicoding.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.githubuser.database.AppDatabase
import com.dicoding.githubuser.database.UserFavoriteDao

class FavoriteProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.dicoding.githubuser"
        const val TABLE_NAME = "favorite"
        const val FAVORITE_ID = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_ID)
        }
    }

    private var favDao: UserFavoriteDao? = null

    override fun onCreate(): Boolean {
        favDao = context?.let {
            AppDatabase.getDatabase(it).getUserFavoriteDao()
        }
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE_ID -> {
                cursor = favDao?.getCursor()
                cursor?.setNotificationUri(context?.contentResolver, uri)
            }
            else -> cursor = null
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

}