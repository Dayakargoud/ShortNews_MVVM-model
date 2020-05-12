package com.dayakar.shortnews.searchnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dayakar.shortnews.Utils.NewsAdapter
import com.dayakar.shortnews.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * A simple [Fragment] subclass.
 */
class SearchNewsFragment : Fragment() {
    lateinit var query:String

    @ExperimentalCoroutinesApi
    private val viewModel: SearchNewsViewModel by lazy {
        query=SearchNewsFragmentArgs.fromBundle(requireArguments()).searchQuery
        ViewModelProvider(this,SearchNewsViewModelFactory(query)).get(SearchNewsViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        binding.searchRecyclerView.adapter =  NewsAdapter()
        viewModel.total_result.observe(viewLifecycleOwner, Observer { results->
            if(results==0){
                binding.searchResults.text="No news found for $query"

            }
        })


        //netwokResponse=viewModel.networkResponseCode.value

        return binding.root  }

}
