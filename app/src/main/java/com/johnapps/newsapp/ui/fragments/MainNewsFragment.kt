package com.johnapps.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.johnapps.newsapp.adapters.CategoryViewAdapter
import com.johnapps.newsapp.adapters.NewsSortByAdapter
import com.johnapps.newsapp.databinding.FragmentMainNewsBinding
import com.johnapps.newsapp.networking.ApiConst
import com.johnapps.newsapp.networking.ApiUtil
import com.johnapps.newsapp.ui.activities.MainActivity
import com.johnapps.newsapp.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.launch
import java.text.ParsePosition


class MainNewsFragment : Fragment() {


    private var _binding: FragmentMainNewsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    private lateinit var viewPager: ViewPager2

    private lateinit var newsCategoryAdapterCategory: CategoryViewAdapter

    private lateinit var categoryList: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainNewsBinding.inflate(inflater, container, false)

        return _binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

       categoryList = ApiConst.getAllCategory()

        viewPager = binding.mainFragmentViewPager

        newsCategoryAdapterCategory = CategoryViewAdapter(this, categoryList)

        viewPager.adapter = newsCategoryAdapterCategory

        val tabLayout = binding.mainFragmentTabLayout

        TabLayoutMediator(tabLayout, viewPager){tab , position ->
            for (i in categoryList.indices){
                tab.text = categoryList[position].replaceFirstChar {
                    it.uppercase()
                }
            }
        }.attach()

        val sortByItems = ApiUtil.getSortByItems()

        val sortByAdapter = NewsSortByAdapter(sortByItems)

        binding.sortByRecycle.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = sortByAdapter
        }

        sortByAdapter.setOnItemClickListener { position ->
            sortNewsBy(position)
        }
    }

    private fun sortNewsBy(position: Int) {
        val sortBy = ApiUtil.getSortByItemName(position)
        viewModel.sortNewsBy(sortBy)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}