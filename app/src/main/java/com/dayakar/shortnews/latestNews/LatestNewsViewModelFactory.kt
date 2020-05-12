package com.dayakar.shortnews.latestNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LatestNewsViewModelFactory(val urlLink:String):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LatestNewsViewModel(urlLink) as T

    }
}