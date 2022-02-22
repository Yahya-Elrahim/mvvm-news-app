package com.johnapps.newsapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.johnapps.newsapp.model.Article
import com.johnapps.newsapp.networking.NewsApi
import retrofit2.HttpException
import java.io.IOException

class NewsDataSource(
    private val api: NewsApi,
    private val mapQuery: Map<String, String>
): PagingSource<Int, Article>(){

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val position = params.key ?: START_PAGE_INDEX

        return try {

            val response = api.getTopHeadlines(mapQuery)

            val result = response.body()?.articles!!

            LoadResult.Page(
                data = result,
                nextKey = if(result.isEmpty()) null else position + 1,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1
            )

        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

    companion object{
        const val START_PAGE_INDEX: Int = 1
    }

}