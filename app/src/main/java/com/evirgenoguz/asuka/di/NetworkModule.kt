package com.evirgenoguz.asuka.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.evirgenoguz.asuka.data.NetworkManager
import com.evirgenoguz.asuka.data.api.AsukaApi
import com.evirgenoguz.asuka.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Oguz Evirgen on 10.04.2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkManager(
        @ApplicationContext application: Context,
        gsonProvider: Provider<Gson>
    ) = NetworkManager(application, gsonProvider)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .let {
            if (BuildConfig.DEBUG) {
                it.addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            } else {
                it
            }
        }
        .callTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .pingInterval(1, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAsukaApi(client: Retrofit): AsukaApi = client.create(AsukaApi::class.java)


    //ToDo: our api provider function will be here for example
//    @Provides
//    @Singleton
//    fun provideSampleApi(client: Retrofit): SampleApi =
//        client.create(SampleApi::class.java)
}