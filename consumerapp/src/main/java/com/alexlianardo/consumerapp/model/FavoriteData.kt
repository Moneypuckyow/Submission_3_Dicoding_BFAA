package com.alexlianardo.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteData(
    var username: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var photo: String? = null,
    var repository: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var favorite: String? = null
): Parcelable