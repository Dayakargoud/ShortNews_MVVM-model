package com.dayakar.shortnews.viewpager

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager

class ViewPagerViewModel(var app:Application):AndroidViewModel(app) {
    val message = MutableLiveData<String>()
    private val _categories=MutableLiveData<MutableList<String>>()
    val categories:LiveData<MutableList<String>> get() = _categories
    private val _itemAdded=MutableLiveData<Boolean>()
    val itemAdded:LiveData<Boolean> get() = _itemAdded

    fun addCategory(msg: String) {
        _itemAdded.value=true
        message.value = msg
        _categories.value?.add(msg)

    }
    fun removeCategory(item:String){
        val isExist=_categories.value?.contains(item)
         if(isExist==true){
             _categories.value?.removeAll { it==item }
         }


    }
    init {
        loadCtegories()

    }
    private fun loadCtegories(){
        _categories.value=getCategories()
    }

    private fun getCategories(): MutableList<String> {
        val list= mutableListOf(
            "headlines",
            "world",
            "business",
            "entertainment",
            "sports",
            "science",
            "technology",
            "politics"
        )
        getalreadyAddedCategories().let { list.addAll(it) }
        return list
    }

    override fun onCleared() {
        super.onCleared()

    }

    fun getalreadyAddedCategories():Set<String>{
        val preferences=PreferenceManager.getDefaultSharedPreferences(app).all

           val list=preferences.filterValues { it==true }.keys

        return list
    }



}