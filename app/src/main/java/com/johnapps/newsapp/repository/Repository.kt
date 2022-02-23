package com.johnapps.newsapp.repository

import com.johnapps.newsapp.model.NewsResponse
import com.johnapps.newsapp.networking.NewsApi
import io.reactivex.Observable

import javax.inject.Inject


class Repository @Inject constructor(private val service: NewsApi) {

   fun getTopHeadLines(options: Map<String, String>): Observable<NewsResponse>{
       return service.getTopHeadlines(options)
   }

}