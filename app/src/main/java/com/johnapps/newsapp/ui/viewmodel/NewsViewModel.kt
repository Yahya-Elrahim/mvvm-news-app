package com.johnapps.newsapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.johnapps.newsapp.model.NewsResponse
import com.johnapps.newsapp.networking.ApiConst
import com.johnapps.newsapp.networking.ApiUtil
import com.johnapps.newsapp.networking.RetrofitClient
import com.johnapps.newsapp.repository.Repository
import com.johnapps.newsapp.utils.AppUtil
import com.johnapps.newsapp.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.LinkedHashMap

class NewsViewModel(
    app: Application
) : AndroidViewModel(app) {

    var newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    private val disposable = CompositeDisposable()

    private val api = RetrofitClient.api

    private val repository = Repository(api)

    val options = LinkedHashMap<String, String>()

    private fun fetchTopHeadlines() {

        try {

            if (AppUtil.hasInternetConnection(getApplication())) {

                newsLiveData.postValue(Resource.Loading())

                disposable.add(repository
                    .getTopHeadLines(options)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        result?.let {
                            newsLiveData.postValue(Resource.Success(result))
                        }
                    })
            } else {
                newsLiveData.postValue(Resource.Error(message = "No Internet Connection"))
            }
        }
        catch (error: Throwable){
            when(error){
                is IOException -> {
                    newsLiveData.postValue(Resource.Error(message = "Network Failure"))
                }
                is HttpException ->{
                    newsLiveData.postValue(Resource.Error(message = "Conversion Error"))
                }
            }
        }
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


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}