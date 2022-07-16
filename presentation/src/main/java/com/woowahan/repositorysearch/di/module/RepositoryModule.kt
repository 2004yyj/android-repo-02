package com.woowahan.repositorysearch.di.module

import androidx.paging.PagingData
import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.issue.IssueDataSourceImpl
import com.woowahan.data.issue.IssueRepositoryImpl
import com.woowahan.data.notification.NotificationDataSourceImpl
import com.woowahan.data.notification.NotificationRepositoryImpl
import com.woowahan.data.user.UserDataSourceImpl
import com.woowahan.data.user.UserRepositoryImpl
import com.woowahan.domain.model.Issue
import com.woowahan.domain.repository.AuthRepository
import com.woowahan.domain.repository.IssueRepository
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
    fun provideApiNotificationRepository(@RetrofitModule.typeApi notificationDataSourceImpl: NotificationDataSourceImpl): NotificationRepository =
        NotificationRepositoryImpl(notificationDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeGitHub
    fun provideGitHubNotificationRepository(@RetrofitModule.typeGitHub notificationDataSourceImpl: NotificationDataSourceImpl): NotificationRepository =
        NotificationRepositoryImpl(notificationDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideUserRepository(@RetrofitModule.typeApi userDataSourceImpl: UserDataSourceImpl): UserRepository =
        UserRepositoryImpl(userDataSourceImpl)

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideIssueRepository(@RetrofitModule.typeApi issueDataSourceImpl: IssueDataSourceImpl): IssueRepository<PagingData<Issue>> =
        IssueRepositoryImpl(issueDataSourceImpl)
}