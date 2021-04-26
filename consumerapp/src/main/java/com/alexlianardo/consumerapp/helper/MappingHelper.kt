package com.alexlianardo.consumerapp.helper

import android.database.Cursor
import com.alexlianardo.consumerapp.database.DatabaseContract
import com.alexlianardo.consumerapp.model.FavoriteData
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteData> {
        val favoriteList = ArrayList<FavoriteData>()

        favCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORIES))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PHOTO))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAVORITE))
                favoriteList.add(FavoriteData(username, name, company, location, photo, repository, followers, following, favorite))
            }
        }
        return favoriteList
    }
}