package com.dayakar.shortnews.searchnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dayakar.shortnews.latestNews.NewsApiStatus
import com.dayakar.shortnews.network.NewsApi
import com.dayakar.shortnews.newsData.Article
import com.dayakar.shortnews.newsData.NewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class SearchNewsViewModel( query: String?):ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<NewsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<NewsApiStatus>
        get() = _status
   private val _total_result=MutableLiveData<Int>()
    val total_result:LiveData<Int> get()=_total_result

    private var _networkResponseCode= MutableLiveData<String>()
    val networkResponseCode: LiveData<String> get() = _networkResponseCode

    private  var _newsList= MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> get() = _newsList

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )


    init {
        if (query != null) {
            loadNewsArticles(query)
        }
    }



    private fun loadNewsArticles(query:String) {

        coroutineScope.launch {
            _status.value= NewsApiStatus.LOADING
            val newsnetworkResponse: Response<NewsResponse>?

            newsnetworkResponse = NewsApi.retrofitService.searchNews(query = query)

            try {
                _status.value= NewsApiStatus.LOADING
                if(newsnetworkResponse.isSuccessful){
                    _total_result.value=newsnetworkResponse.body()?.articles?.size
                _newsList.value = newsnetworkResponse.body()?.articles
                _status.value= NewsApiStatus.DONE}else{
                    _total_result.value=0
                    _status.value= NewsApiStatus.DONE
                }

            } catch (e: Exception) {
                _total_result.value=0
                _networkResponseCode.value=newsnetworkResponse.message()
                _status.value= NewsApiStatus.ERROR


            }
        }


    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}