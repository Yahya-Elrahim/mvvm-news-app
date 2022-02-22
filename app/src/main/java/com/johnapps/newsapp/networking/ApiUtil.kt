package com.johnapps.newsapp.networking

import com.johnapps.newsapp.model.SortItem
import java.util.*

class ApiUtil {


    companion object{

        fun getDefaultCountry(): String{
            return Locale.getDefault().country.lowercase()
        }

        fun getSortByItems(): ArrayList<SortItem> {
            return arrayListOf(
                SortItem("Popularity", true),
                SortItem("Relevancy"),
                SortItem("Newest")

            )
        }

        fun getSortByItemName(position: Int): String{
            return sortItems()[position]
        }

        private fun sortItems(): Array<String> {
            return arrayOf(
                "popularity",
                "relevancy",
                "publishedAt"
            )
        }


    }
}