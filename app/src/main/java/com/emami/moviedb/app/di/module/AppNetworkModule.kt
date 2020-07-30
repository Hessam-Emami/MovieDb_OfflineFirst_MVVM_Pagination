package com.emami.moviedb.app.di.module

import com.emami.moviedb.BuildConfig
import com.emami.moviedb.app.di.qualifier.ConnectivityCheckerInterceptorQualifier
import com.emami.moviedb.app.di.qualifier.KeyInterceptorQualifier
import com.emami.moviedb.app.di.qualifier.LoggingInterceptorQualifier
import com.emami.moviedb.common.util.ConnectivityChecker
import com.emami.moviedb.common.util.HeaderQueryUtil
import com.emami.moviedb.common.util.NoConnectivityException
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *Provides networking requirements, Also there is no need to use [JvmStatic] annotation in
 *the newest dagger versions
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppNetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @LoggingInterceptorQualifier
    @Singleton
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    @ConnectivityCheckerInterceptorQualifier
    //Checks connectivity before the every call shoots onto the network
    fun provideInternetConnectivityInterceptor(connectivityChecker: ConnectivityChecker): Interceptor =
        Interceptor {
            if (!connectivityChecker.isNetworkActive()) throw NoConnectivityException()
            it.proceed(it.request())
        }

    //Intercepts each request and adds required auth keys to the header
    @Provides
    @KeyInterceptorQualifier
    @Singleton
    fun provideKeyInterceptor(): Interceptor = Interceptor.invoke { chain ->
        val request = HeaderQueryUtil.addSecretQueryParams(
            chain.request(),
            BuildConfig.API_KEY
        )
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @KeyInterceptorQualifier keyInterceptor: Interceptor,
        @ConnectivityCheckerInterceptorQualifier connectivityInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            addInterceptor(keyInterceptor)
            addInterceptor(connectivityInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}