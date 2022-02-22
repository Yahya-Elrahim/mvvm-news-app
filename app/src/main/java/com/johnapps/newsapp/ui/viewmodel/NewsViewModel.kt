package com.johnapps.newsapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.johnapps.newsapp.model.NewsResponse
import com.johnapps.newsapp.networking.ApiConst
import com.johnapps.newsapp.networking.ApiUtil
import com.johnapps.newsapp.networking.RetrofitClient
import com.johnapps.newsapp.repository.Repository
import com.johnapps.newsapp.utils.AppUtil
import com.johnapps.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.LinkedHashMap

class NewsViewModel(
    app: Application
) : AndroidViewModel(app) {

    var newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    private val api = RetrofitClient.api

    private val repository = Repository(api)

    val options = LinkedHashMap<String, String>()

    private fun fetchTopHeadlines() {

        viewModelScope.launch {

            newsLiveData.postValue(Resource.Loading())

            try {
                if (AppUtil.hasInternetConnection(getApplication())){

                    val response = repository.getTopHeadLines(options)

                    newsLiveData.postValue(handleResponse(response))
                }
                else{
                    newsLiveData.postValue(Resource.Error(message = "No Internet Connection"))
                }
            }catch (t: Throwable){
                when(t){
                    is IOException -> {
                        newsLiveData.postValue(Resource.Error(message = "Network Failure"))
                    }
                    is HttpException ->{
                        newsLiveData.postValue(Resource.Error(message = "Conversion Error"))
                    }
                }
            }




        }
    }

    private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse>{

        if (response.isSuccessful){

            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(null, response.message())

    }




    fun fetchNewsWithCategory(category: String)
    {

        options[ApiConst.COUNTRY] = ApiUtil.getDefaultCountry()
        options[ApiConst.CATEGORY] = category

        fetchTopHeadlines()
    }

    fun sortNewsBy(sortBy: String) {

        options[ApiConst.COUNTRY] = ApiUtil.getDefaultCountry()
        options[ApiConst.SORTBY] = sortBy

        fetchTopHeadlines()
    }




}