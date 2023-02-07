package com.example.berteandroid.system.web

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

class WebService {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
    var gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()
    private val retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    private val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }

    suspend fun getData() = retrofitService.getPhotos()
}

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<String>
}