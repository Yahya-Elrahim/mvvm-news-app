package com.johnapps.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnapps.newsapp.R
import com.johnapps.newsapp.model.SortItem
import com.johnapps.newsapp.utils.ResourceUtil
import kotlinx.android.synthetic.main.item_news_sort_by.view.*

class NewsSortByAdapter(private val items: ArrayList<SortItem>): RecyclerView.Adapter<NewsSortByAdapter.SortViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    class SortViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        return SortViewHolder(

            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_sort_by, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {

        val item = items[position]

        holder.itemView.apply {

            sort_by_item.text = item.name

            if (item.isSelected) {
                sort_by_item.setBackgroundResource(R.drawable.abc_selected_item)
                sort_by_item.setTextColor(ResourceUtil.getColor(context, R.color.white))
            }
            else {
                sort_by_item.setBackgroundResource(R.drawable.abc_unselected_item)
                sort_by_item.setTextColor(ResourceUtil.getColor(context, R.color.black))
            }


            setOnClickListener {
                onItemClickListener?.let {
                    clearSelectedItems(position)
                    it(position)

                }
            }
        }
    }

    private fun clearSelectedItems(position: Int) {
        for (item in items)
            item.isSelected = false

        items[position].isSelected = true
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }
}