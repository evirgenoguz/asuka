package com.evirgenoguz.asuka.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Oguz Evirgen on 10.04.2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    //ToDo: in this part we will create repository provider for example
//    @Provides
//    @Singleton
//    fun providesSampleRepository(
//        apiService: SampleApi,
//        networkManager: NetworkManager
//    ): SampleRepository =
//        SampleRepository(apiService, networkManager)


}