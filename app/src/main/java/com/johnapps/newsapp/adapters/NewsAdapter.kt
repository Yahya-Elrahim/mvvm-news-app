package com.johnapps.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnapps.newsapp.R
import com.johnapps.newsapp.databinding.ItemNewsBinding
import com.johnapps.newsapp.databinding.ItemNewsTopBinding
import com.johnapps.newsapp.model.Article
import com.johnapps.newsapp.utils.getDateFromString
import kotlinx.android.synthetic.main.item_news.view.*
import kotlinx.android.synthetic.main.item_news_top.view.*
import kotlinx.android.synthetic.main.item_news_top.view.tvPublishedAt
import kotlinx.android.synthetic.main.item_news_top.view.tvSource


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.MyHolderView>() {

    private val TOP_NEWS: Int = 0
    private val OTHER: Int = 1

    private var onItemClickListener: ((Article) -> Unit)? = null



    class MyHolderView(private val binding: View): RecyclerView.ViewHolder(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {

        return when(viewType) {

            0 -> {
                MyHolderView(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_news_top, parent, false)
                )
            }else ->{
                MyHolderView(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_news, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: MyHolderView, position: Int) {

        val article = differ.currentList[position]

        when(position) {
           0 -> holder.itemView.apply {
                article?.let {
                    Glide.with(context).load(it.imageUrl).into(imageViewMain)
                    tvContent.text = it.description
                    tvSource.text = it.source?.name
                    tvTitle.text = it.title
                    tvPublishedAt.text = it.publishDate.getDateFromString().toString()

                    setOnClickListener {
                        onItemClickListener?.let {
                            it(article)
                        }
                    }
                }
            }
            else -> holder.itemView.apply {
                article?.let {
                    Glide.with(context).load(it.imageUrl).into(imageView)

                    tvSource.text = it.source?.name
                    tvDescription.text = it.title
                    tvPublishedAt.text = it.publishDate.getDateFromString().toString()

                    setOnClickListener {
                        onItemClickListener?.let {
                            it(article)
                        }
                    }
                }
            }

        }


    }



    companion object{

        val diffUtil = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TOP_NEWS else OTHER
    }

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}