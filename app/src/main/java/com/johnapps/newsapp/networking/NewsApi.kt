package com.johnapps.newsapp.networking

import com.johnapps.newsapp.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getTopHeadlines(
        @QueryMap options: Map<String, String>,
        @Query("pageSize") pageSize: Int = ApiConst.DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = ApiConst.NEWS_API_KEY
    ): Observable<NewsResponse>

    @GET("/v2/everything")
    fun search(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = ApiConst.NEWS_API_KEY
    ): Observable<NewsResponse>


}