package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthService
import com.woowahan.data.notification.NotificationService
import com.woowahan.data.user.UserService
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
    @RetrofitModule.typeGitHub
    fun provideAuthService(@RetrofitModule.typeGitHub retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @RetrofitModule.typeApi
    fun provideNotificationService(@RetrofitModule.typeApi retrofit: Retrofit): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }

    @Provides
    @Singleton
    @RetrofitModule.typeApi
    fun provideUserService(@RetrofitModule.typeApi retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}