package com.johnapps.newsapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.internal.bind.util.ISO8601Utils
import com.johnapps.newsapp.R
import java.text.ParsePosition
import java.util.*


fun getCircularProgressBar(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        setTint(ResourceUtil.getColor(context, R.color.cardview_shadow_end_color))
        start()
    }
}
fun ImageView.loadImage(url: String, context: Context){

    /*val requestOption = RequestOptions()
        .placeholder(getCircularProgressBar(context))
        .error(R.drawable.ic_launcher_background)*/

    Glide.with(context)
           .load(url)
        .into(this)
}


fun loadImage(imageView: ImageView, url: String?){
    url?.let {
        imageView.loadImage(url, imageView.context)
    }
}

fun String.getDateFromString() : Date? = ISO8601Utils.parse(this ,
    ParsePosition(0)
)