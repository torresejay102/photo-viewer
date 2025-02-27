package com.eshow.photoviewer.network.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.eshow.photoviewer.PhotoViewerApplication
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkService {
    private const val BASE_URL = "https://json-placeholder.mock.beeceptor.com"
    private const val CACHE_SIZE = (5 * 1024 * 1024).toLong()
    private const val MAX_AGE = 300
    private const val MAX_STALE = 60 * 60 * 24 * 7

    private val networkJson = Json {ignoreUnknownKeys = true}
    private val cache = Cache(PhotoViewerApplication.applicationContext().cacheDir, CACHE_SIZE)

    private fun isOnline(): Boolean {
        val context = PhotoViewerApplication.applicationContext()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (isOnline())
                request.newBuilder().header("Cache-Control", "public, max-age=" +
                        MAX_AGE
                ).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, " +
                        "max-stale=" + MAX_STALE
                ).build()
            chain.proceed(request)
        }
        .build()

    val userListService: UserListService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(UserListService::class.java)
    }
}