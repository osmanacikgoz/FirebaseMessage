package com.osmanacikgoz.firebaserealtimeexample.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String?,
    val profileImageUrl: String?,
    val username: String?
):Parcelable{constructor() : this("","","")}

