package com.alexlianardo.github2.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.alexlianardo.github2"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns{
        companion object {
            const val TABLE_NAME = "favGithubApp"
            const val USERNAME = "username"
            const val NAME = "name"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORIES = "repositories"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val PHOTO = "photo"
            const val FAVORITE = "fav"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}