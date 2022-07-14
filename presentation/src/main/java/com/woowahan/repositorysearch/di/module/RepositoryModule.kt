package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.notification.NotificationDataSourceImpl
import com.woowahan.data.notification.NotificationRepositoryImpl
import com.woowahan.domain.repository.AuthRepository
import com.woowahan.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    @RetrofitModule.typeAuth
    fun provideAuthRepository(@RetrofitModule.typeAuth authDataSourceImpl: AuthDataSourceImpl): AuthRepository =
        AuthRepositoryImpl(authDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideNotificationRepository(@RetrofitModule.typeApi notificationDataSourceImpl: NotificationDataSourceImpl): NotificationRepository =
        NotificationRepositoryImpl(notificationDataSourceImpl)
}