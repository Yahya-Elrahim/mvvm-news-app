package com.johnapps.newsapp.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Article(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @SerializedName("source")
    val source: Source?,

    @SerializedName("author")
    val author: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("urlToImage")
    val imageUrl: String = "",

    @SerializedName("publishedAt")
    val publishDate: String = "",

    @SerializedName("content")
    val content: String = "",
): Serializable