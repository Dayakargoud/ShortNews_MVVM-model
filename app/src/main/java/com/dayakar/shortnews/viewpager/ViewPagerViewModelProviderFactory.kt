package com.dayakar.shortnews.viewpager

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewPagerViewModelProviderFactory(var app:Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewPagerViewModel(app) as T
    }
}