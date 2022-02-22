package com.johnapps.newsapp.adapters


import android.os.Bundle
import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.johnapps.newsapp.networking.ApiConst
import com.johnapps.newsapp.ui.fragments.NewsFragment
import com.johnapps.newsapp.utils.Constants

class CategoryViewAdapter(
    fm: Fragment,
    private val list: Array<String>
): FragmentStateAdapter(fm){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return newInstance(position)
    }

    private fun newInstance(position: Int): Fragment{
        return NewsFragment.newInstance(list[position])
    }


}