package com.dayakar.shortnews.newsData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable{}