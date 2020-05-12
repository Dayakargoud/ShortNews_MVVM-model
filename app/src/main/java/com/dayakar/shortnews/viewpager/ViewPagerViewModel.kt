package com.dayakar.shortnews.viewpager

import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager

class ViewPagerViewModel:ViewModel() {
    val message = MutableLiveData<String>()
    private val _categories=MutableLiveData<MutableList<String>>()
    val categories:LiveData<MutableList<String>> get() = _categories

    fun addCategory(msg: String) {
        message.value = msg
        _categories.value?.add(msg)
    }
    init {
        loadCtegories()
    }
    private fun loadCtegories(){
        _categories.value=getCategories()
    }

    private fun getCategories(): MutableList<String> {
        return mutableListOf(
            "headlines",
            "business",
            "entertainment",
            "sports",
            "science",
            "technology",
            "politics",
            "health"
        )


    }


    override fun onCleared() {
        super.onCleared()
        Log.d("ViewPager","View pager model is destroyed")

    }
    fun getalreadyAddedCategories(){

    }

}