package com.example.berteandroid.system.web

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

class WebService {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
    private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL)
}

interface MarsApiService {
    @GET("photos")
    fun getPhotos(): String
}