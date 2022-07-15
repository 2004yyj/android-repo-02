package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.notification.NotificationDataSourceImpl
import com.woowahan.data.notification.NotificationRepositoryImpl
import com.woowahan.data.user.UserDataSourceImpl
import com.woowahan.data.user.UserRepositoryImpl
import com.woowahan.domain.repository.AuthRepository
import com.woowahan.domain.repository.NotificationRepository
import com.woowahan.domain.repository.UserRepository
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
    @RetrofitModule.typeGitHub
    fun provideAuthRepository(@RetrofitModule.typeGitHub authDataSourceImpl: AuthDataSourceImpl): AuthRepository =
        AuthRepositoryImpl(authDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideNotificationRepository(@RetrofitModule.typeApi notificationDataSourceImpl: NotificationDataSourceImpl): NotificationRepository =
        NotificationRepositoryImpl(notificationDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideUserRepository(@RetrofitModule.typeApi userDataSourceImpl: UserDataSourceImpl): UserRepository =
        UserRepositoryImpl(userDataSourceImpl)
}