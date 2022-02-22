package com.johnapps.newsapp.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnapps.newsapp.R
import com.johnapps.newsapp.adapters.NewsAdapter
import com.johnapps.newsapp.databinding.FragmentNewsBinding
import com.johnapps.newsapp.model.Article
import com.johnapps.newsapp.networking.ApiConst
import com.johnapps.newsapp.networking.ApiConst.QUERY_PAGE_SIZE
import com.johnapps.newsapp.networking.ApiUtil
import com.johnapps.newsapp.ui.activities.MainActivity
import com.johnapps.newsapp.ui.viewmodel.NewsViewModel
import com.johnapps.newsapp.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter

    private lateinit var viewModel: NewsViewModel

    private lateinit var category: String



    companion object{
        @JvmStatic
        fun newInstance(args: String): NewsFragment{
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ApiConst.CATEGORY, args)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.let {
            it.getString(ApiConst.CATEGORY)
        } ?: ApiConst.CATEGORY
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentNewsBinding.inflate(inflater, container, false)


        return _binding!!.root
    }

    private fun initViews() {

        viewModel = (activity as MainActivity).viewModel

        newsAdapter = NewsAdapter()

        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(R.id.action_newsFragment_to_detailsFragment, bundle)

        }


        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

       binding.tryAgain.setOnClickListener{
           fetchNews()
       }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()

        setupViewModelObserver()

        fetchNews()


    }

    private fun fetchNews() {
        viewModel.fetchNewsWithCategory(category)
    }


 /*   private fun setupViewModelObserver() {

        viewModel.newsLiveData.observe(this@NewsFragment, Observer {
            it?.let { submitNews(it) }
        })
    }

    private fun submitNews(it: PagingData<Article>) {
        lifecycleScope.launch {
            newsAdapter.submitData(it)
        }
    }*/

      private fun setupViewModelObserver() {

         viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { response ->

             when(response){

                 is Resource.Success ->{
                     Log.i("Tag Status", "Succedd")
                     response.data?.let { data ->

                         binding.shimmerLayout.apply {
                             visibility = View.GONE
                             stopShimmer()
                         }
                         binding.loadError.apply {
                             visibility = View.GONE
                         }

                         newsAdapter.differ.submitList(data.articles.toList())

                     }
                 }

                 is Resource.Error ->{
                     binding.shimmerLayout.apply {
                         visibility = View.GONE
                         stopShimmer()
                     }
                     binding.loadError.apply {
                         visibility = View.VISIBLE
                     }
                     binding.errorMessage.text = response.message
                 }

                 is Resource.Loading ->{
                     binding.shimmerLayout.apply {
                         visibility = View.VISIBLE
                         stopShimmer()
                     }
                     binding.loadError.apply {
                         visibility = View.GONE
                     }

                     Log.i("Tag Status", "Loading")
                 }
             }
         })
      }


}