package com.johnapps.newsapp.networking

object ApiConst {

    const val DEFAULT_PAGE_SIZE: Int = 100

    const val SORTBY: String = "sortBy"

    const val COUNTRY: String = "country"
    const val CATEGORY: String = "category"

    const val DEFAULT_CATEGORY: String = "general"

    const val BASE_URL: String = "https://newsapi.org/"

    const val NEWS_API_KEY: String = "2be04e7443ec4dd496677006caa9c2b7"

    const val SEARCH_TIME_DELAY: Int = 500

    const val QUERY_PAGE_SIZE = 20


    fun getAllCategory(): Array<String> {
        return arrayOf(
            "general",
            "entertainment",
            "business",
            "health",
            "science",
            "sports",
            "technology"
        )
    }
}