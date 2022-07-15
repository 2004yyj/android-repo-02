package com.woowahan.repositorysearch.di.module

import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.domain.repository.AuthRepository
import com.woowahan.domain.repository.NotificationRepository
import com.woowahan.domain.repository.UserRepository
import com.woowahan.domain.userUseCase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    @RetrofitModule.typeGitHub
    fun provideGetGitHubAccessTokenUseCase(@RetrofitModule.typeGitHub repository: AuthRepository): GetGitHubAccessTokenUseCase {
        return GetGitHubAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetNotificationsUseCase(@RetrofitModule.typeApi repository: NotificationRepository): GetNotificationsUseCase {
        return GetNotificationsUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeGitHub
    fun provideMarkNotificationAsReadUseCase(@RetrofitModule.typeGitHub repository: NotificationRepository): MarkNotificationAsReadUseCase {
        return MarkNotificationAsReadUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetSubjectUseCase(@RetrofitModule.typeApi repository: NotificationRepository): GetSubjectUseCase {
        return GetSubjectUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetUserUseCase(@RetrofitModule.typeApi repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }
}