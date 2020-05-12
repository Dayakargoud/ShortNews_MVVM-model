package com.dayakar.shortnews.searchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchNewsViewModelFactory(val query:String?):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchNewsViewModel(query) as T
    }
}