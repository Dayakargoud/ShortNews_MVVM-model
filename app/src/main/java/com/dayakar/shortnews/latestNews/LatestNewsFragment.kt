package com.dayakar.shortnews.latestNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer

import com.dayakar.shortnews.Utils.NewsAdapter
import com.dayakar.shortnews.databinding.FragmentLatestNewsBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dayakar.shortnews.R
import com.dayakar.shortnews.viewpager.ViewPagerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.util.Pair as UtilPair


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
        binding.latestNewsRecyclerView.adapter =  NewsAdapter()
        viewModel.networkResponseCode.observe(viewLifecycleOwner, Observer {
            response-> netwokResponse=response
            Toast.makeText(context,netwokResponse,Toast.LENGTH_SHORT).show()

        })
        //netwokResponse=viewModel.networkResponseCode.value
        return binding.root
    }

}
