package com.example.mediaplayer.data.network

import com.example.mediaplayer.data.model.Property
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Flowable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val client: OkHttpClient = OkHttpClient.Builder()
    .build()

val rxAdapter: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .addCallAdapterFactory(rxAdapter)
    .baseUrl(BASE_URL)
    .build()


interface MusicApiService {
    @GET("search?")
    fun getSearchProperty(
        @Query("term") term:String
    ): Flowable<Property>


    @GET("search?")
    suspend fun coroutineSearch(
        @Query("term") term:String
    ): Property
}

object MusicApi {
    val retrofitService: MusicApiService by lazy {
        retrofit.create(MusicApiService::class.java)
    }
}