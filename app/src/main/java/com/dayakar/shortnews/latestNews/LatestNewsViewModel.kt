package com.dayakar.shortnews.latestNews

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dayakar.shortnews.newsData.Article
import com.dayakar.shortnews.network.NewsApi
import com.dayakar.shortnews.newsData.NewsResponse
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception


enum class NewsApiStatus{
    LOADING,
    DONE,
    ERROR
}


class LatestNewsViewModel(args:String) :ViewModel(){
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<NewsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<NewsApiStatus>
        get() = _status

    private var _networkResponseCode=MutableLiveData<String>()
    val networkResponseCode:LiveData<String> get() = _networkResponseCode

    private  var _newsList=MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> get() = _newsList

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )


    init {
        loadNewsArticles(args)
    }



    private fun loadNewsArticles(category:String) {

        coroutineScope.launch {
            _status.value=NewsApiStatus.LOADING
            val newsnetworkResponse: Response<NewsResponse>?

            newsnetworkResponse = when(category){
                "headlines"->{
                    NewsApi.retrofitService.getTopHeadlines()
                }
                else->{
                    NewsApi.retrofitService.getCategoryNews(category = category)
                }
            }


            try {
                _status.value=NewsApiStatus.LOADING
                _newsList.value = newsnetworkResponse.body()?.articles
                Log.d("Latest View model","Body = ${newsnetworkResponse.body().toString()}")
                _status.value=NewsApiStatus.DONE

            } catch (e: Exception) {
               _networkResponseCode.value=newsnetworkResponse.message()
                Log.d("Latest View model","Exception -> ${e.message}")
                _status.value=NewsApiStatus.ERROR

            }
        }


    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}