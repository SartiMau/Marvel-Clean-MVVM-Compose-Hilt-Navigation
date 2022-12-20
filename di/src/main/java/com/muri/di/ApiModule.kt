package com.muri.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.muri.data.BuildConfig
import com.muri.data.service.api.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val TS = "ts"
    private const val API = "apikey"
    private const val HASH = "hash"
    private const val TS_VALUE = "1"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val defaultRequest = chain.request()
                val defaultHttpUrl = defaultRequest.url
                val httpUrl = defaultHttpUrl.newBuilder()
                    .addQueryParameter(TS, TS_VALUE)
                    .addQueryParameter(API, BuildConfig.API_VALUE)
                    .addQueryParameter(HASH, BuildConfig.HASH_VALUE)
                    .build()
                val requestBuilder = defaultRequest.newBuilder().url(httpUrl)
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @ExperimentalSerializationApi
    fun provideConverterFactory(): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    fun provideMarvelApi(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): MarvelApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.MARVEL_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create(MarvelApi::class.java)
    }

    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }
}
