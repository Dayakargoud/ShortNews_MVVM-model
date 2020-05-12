package com.dayakar.shortnews.Utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dayakar.shortnews.newsData.Article
import com.dayakar.shortnews.databinding.NewsItemBinding
import androidx.navigation.fragment.findNavController
import com.dayakar.shortnews.*

class NewsAdapter:ListAdapter<Article,NewsAdapter.ViewHolder>(NewsDiffUtilCallBack) {

  lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        return ViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context)))    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{

             //holder.itemView.findNavController().navigate(LatestNewsFragmentDirections.actionLatestNewsFragmentToNewsDetailsFragment(getItem(position)))
           // holder.itemView.findNavController().navigate(FragmentViewPagerDirections.actionFragmentViewPager2ToNewsDetailsFragment2(getItem(position)))

           val args= Bundle().apply {
                putParcelable("article", getItem(position))
            }

            holder.itemView.findNavController().navigate(R.id.newsDetailsFragment,args)

        }
    }

    inner class ViewHolder(private val binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){


       fun bind(article:Article){

           binding.article=article
           binding.executePendingBindings()

       }
    }

    companion object NewsDiffUtilCallBack :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

}