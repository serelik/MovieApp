package com.serelik.movieapp.data.network

import com.serelik.movieapp.KeyStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {

    val apiKey = KeyStorage.apiKey

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", apiKey)
            .build()

        val request = Request.Builder().url(url).build()
        return chain.proceed(request)
    }
}
