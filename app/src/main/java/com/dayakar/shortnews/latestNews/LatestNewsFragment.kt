package com.dayakar.shortnews.latestNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.Observer

import com.dayakar.shortnews.Utils.NewsAdapter
import com.dayakar.shortnews.databinding.FragmentLatestNewsBinding
import androidx.lifecycle.ViewModelProvider

import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * A simple [Fragment] subclass.
 */
class LatestNewsFragment() : Fragment() {
    private var netwokResponse:String?=null

    @ExperimentalCoroutinesApi
    private val viewModel: LatestNewsViewModel by lazy {
        val url= arguments?.getString("link")
        ViewModelProvider(this,LatestNewsViewModelFactory(url!!)).get(LatestNewsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = FragmentLatestNewsBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        var adapter=NewsAdapter()
        binding.latestNewsRecyclerView.adapter =  adapter

        viewModel.networkResponseCode.observe(viewLifecycleOwner, Observer {
            response-> netwokResponse=response
            Toast.makeText(context,netwokResponse,Toast.LENGTH_SHORT).show()

        })
        //netwokResponse=viewModel.networkResponseCode.value
        return binding.root
    }

}
