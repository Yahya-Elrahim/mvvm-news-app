package com.johnapps.newsapp.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import com.johnapps.newsapp.R

object ResourceUtil {

    fun getColor(context: Context, color: Int): Int {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            context.getColor(color)
        }else{
            context.resources.getColor(color)
        }
    }
}