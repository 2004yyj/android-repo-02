package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    @RetrofitModule.typeAuth
    fun provideAuthService(@RetrofitModule.typeAuth retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}