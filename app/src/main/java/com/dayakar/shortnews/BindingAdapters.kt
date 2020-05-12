package com.dayakar.shortnews

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dayakar.shortnews.newsData.Article
import com.dayakar.shortnews.Utils.NewsAdapter
import com.dayakar.shortnews.latestNews.NewsApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Article>?) {
    val adapter=recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(img:ImageView,imgUrl:String?) {
    imgUrl.let {
        Glide.with(img.context)
            .load(imgUrl)
            .into(img)
    }
}


@BindingAdapter("newsApiStatus")
fun bindStatus(statusProgress: ProgressBar, status: NewsApiStatus?) {
    when (status) {
        NewsApiStatus.LOADING -> {
            statusProgress.visibility = View.VISIBLE
            //statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsApiStatus.ERROR -> {
            statusProgress.visibility = View.VISIBLE
            //statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsApiStatus.DONE -> {
            statusProgress.visibility = View.GONE
        }
    }
}