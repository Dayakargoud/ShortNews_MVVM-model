package com.dayakar.shortnews.network

import com.dayakar.shortnews.newsData.NewsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val URL_LINK =
    "https://newsapi.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(URL_LINK)
    .build()


interface NewsApiService {
//?country=in&page=1&apiKey=a3a7c65515f0426da9c5e5091c91ad27
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country:String="in",
        @Query("page")
        page:Int=1,
        @Query("apiKey")
        apikey:String=Constants.API_KEY): Response<NewsResponse>

    @GET("v2/top-headlines")
   suspend fun getCategoryNews(
        @Query("country")
        country:String="in",
        @Query("category")
        category:String="entertainment",
        @Query("page")
        page:Int=1,
        @Query("apiKey")
        apikey:String=Constants.API_KEY): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        query:String="android",
        @Query("page")
        page:Int=1,
        @Query("apiKey")
        apikey:String=Constants.API_KEY): Response<NewsResponse>


}

object NewsApi{
    val retrofitService:NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}