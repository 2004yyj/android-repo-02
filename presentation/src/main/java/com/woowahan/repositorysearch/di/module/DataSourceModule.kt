package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthService
import com.woowahan.data.issue.IssueDataSourceImpl
import com.woowahan.data.issue.IssueService
import com.woowahan.data.notification.NotificationDataSourceImpl
import com.woowahan.data.notification.NotificationService
import com.woowahan.data.user.UserDataSourceImpl
import com.woowahan.data.user.UserService
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
    @RetrofitModule.typeGitHub
    fun provideAuthDataSource(@RetrofitModule.typeGitHub authService: AuthService): AuthDataSourceImpl {
        return AuthDataSourceImpl(authService)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideApiNotificationDataSource(@RetrofitModule.typeApi notificationService: NotificationService): NotificationDataSourceImpl {
        return NotificationDataSourceImpl(notificationService)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideUserDataSource(@RetrofitModule.typeApi userService: UserService): UserDataSourceImpl {
        return UserDataSourceImpl(userService)
    }

    @Singleton
    @Provides
    @RetrofitModule.typeApi
    fun provideIssueDataSource(@RetrofitModule.typeApi issueService: IssueService): IssueDataSourceImpl {
        return IssueDataSourceImpl(issueService)
    }
}