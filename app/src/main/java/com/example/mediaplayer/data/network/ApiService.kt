package com.example.mediaplayer.data.network

import com.example.mediaplayer.data.model.Property
import com.example.mediaplayer.data.model.Result
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val client: OkHttpClient = OkHttpClient.Builder()
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


//https://itunes.apple.com/search?term=SEARCH_KEYWORD
interface MusicApiService {
    @GET("search?")
    suspend fun getSearchProperty(
        @Query("term") term:String
    ): Property

}

object MusicApi {
    val retrofitService: MusicApiService by lazy {
        retrofit.create(MusicApiService::class.java)
    }
}