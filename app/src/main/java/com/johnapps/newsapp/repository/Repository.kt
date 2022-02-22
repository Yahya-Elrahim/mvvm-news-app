package com.johnapps.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.johnapps.newsapp.model.Article
import com.johnapps.newsapp.model.NewsResponse
import com.johnapps.newsapp.networking.NewsApi
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


class Repository(private val service: NewsApi) {

   suspend fun getTopHeadLines(options: Map<String, String>): Response<NewsResponse>{
       return service.getTopHeadlines(options)
   }

   suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse>{
      return service.search(query, pageNumber)
   }

   fun getTopHeadlines(options: Map<String, String>): Flow<PagingData<Article>> {
      return Pager(
         config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
         ),
         pagingSourceFactory = {NewsDataSource(service, options)}
      ).flow
   }
}