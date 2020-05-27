package com.dayakar.shortnews.viewpager

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.icu.text.Transliterator
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dayakar.shortnews.databinding.FragmentViewPagerBinding
import com.dayakar.shortnews.latestNews.LatestNewsFragment
import com.dayakar.shortnews.network.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class FragmentViewPager : Fragment() {

    lateinit var categoryList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = FragmentViewPagerBinding.inflate(inflater)
        val viewModel = activity?.let { ViewModelProvider(it,ViewPagerViewModelProviderFactory(requireActivity().application)).get(ViewPagerViewModel::class.java) }

        binding.setLifecycleOwner(this)
        val categories=viewModel?.categories?.value

        if (categories != null) {
            categoryList=categories
        }
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager


        if(isInternetOn()) {
            viewPager.adapter = PagerSliderAdapter(this)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = categoryList[position]

            }.attach()

        }else{
            binding.connection.visibility=View.VISIBLE
            binding.connectionText.visibility=View.VISIBLE
          //  Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show()
            //Snackbar.make(binding.root,"No internet connection",Snackbar.LENGTH_SHORT).show()
        }
        viewModel?.itemAdded?.observe(viewLifecycleOwner, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                viewPager.setCurrentItem(categoryList.size ,true)
                tabLayout.getTabAt(categoryList.size)?.select()
            }

        })
         setHasOptionsMenu(true)

        return binding.root

    }

    override fun onResume() {
        super.onResume()
    }

   private fun isInternetOn(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

   inner class PagerSliderAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {

            val fragment = LatestNewsFragment()
            fragment.arguments = Bundle().apply {
               putString("link", categoryList[position])

            }

            return fragment

        }


    }


}