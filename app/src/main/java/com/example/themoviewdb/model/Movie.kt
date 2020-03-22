package com.example.themoviewdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val poster_path: String?,
    val genre_ids: List<Int>,
    val title: String,
    val backdrop_path: String?
) : Parcelable