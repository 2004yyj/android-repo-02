package com.woowahan.repositorysearch.di.module

import androidx.paging.PagingData
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import com.woowahan.domain.issueUseCase.GetIssueUseCase
import com.woowahan.domain.model.Issue
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Repository
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.domain.repository.*
import com.woowahan.domain.searchUseCase.GetSearchResultUseCase
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
    fun provideGetNotificationsUseCase(@RetrofitModule.typeApi repository: NotificationRepository<PagingData<Notification>>): GetNotificationsUseCase<PagingData<Notification>> {
        return GetNotificationsUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideMarkNotificationAsReadUseCase(@RetrofitModule.typeApi repository: NotificationRepository<PagingData<Notification>>): MarkNotificationAsReadUseCase<PagingData<Notification>> {
        return MarkNotificationAsReadUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetSubjectUseCase(@RetrofitModule.typeApi repository: NotificationRepository<Any?>): GetSubjectUseCase {
        return GetSubjectUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetUserUseCase(@RetrofitModule.typeApi repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetIssueUseCase(@RetrofitModule.typeApi repository: IssueRepository<PagingData<Issue>>): GetIssueUseCase<PagingData<Issue>> {
        return GetIssueUseCase(repository)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideGetSearchResultUseCase(@RetrofitModule.typeApi repository: SearchRepository<PagingData<Repository>>): GetSearchResultUseCase<PagingData<Repository>> {
        return GetSearchResultUseCase(repository)
    }
}