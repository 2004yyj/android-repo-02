package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthService
import com.woowahan.data.notification.NotificationDataSourceImpl
import com.woowahan.data.notification.NotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    @RetrofitModule.typeAuth
    fun provideAuthDataSource(@RetrofitModule.typeAuth authService: AuthService): AuthDataSourceImpl {
        return AuthDataSourceImpl(authService)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideNotificationDataSource(@RetrofitModule.typeApi notificationService: NotificationService): NotificationDataSourceImpl {
        return NotificationDataSourceImpl(notificationService)
    }
}