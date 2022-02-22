package com.johnapps.newsapp.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    var articles: MutableList<Article>,

    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int
)