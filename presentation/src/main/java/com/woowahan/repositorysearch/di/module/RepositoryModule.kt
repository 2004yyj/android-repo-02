package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.auth.AuthService
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
    fun provideAuthRepository(authService: AuthService) =
        AuthRepositoryImpl(authService)
}