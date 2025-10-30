package com.example.karen_and.network

import android.content.Context
import com.example.karen_and.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = BuildConfig.API_URL
    private lateinit var retrofit: Retrofit

    fun init(context: Context) {
        val prefs = context.getSharedPreferences("karen_prefs", Context.MODE_PRIVATE)
        val sessionStore = com.example.karen_and.data.SessionStore(prefs)

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionStore))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(service: Class<T>): T {
        check(::retrofit.isInitialized) { "RetrofitClient no inicializado; llamá init(context) una vez." }
        return retrofit.create(service)
    }
}