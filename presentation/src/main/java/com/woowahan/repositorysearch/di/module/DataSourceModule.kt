package com.woowahan.repositorysearch.di.module

import com.woowahan.data.auth.AuthDataSourceImpl
import com.woowahan.data.auth.AuthService
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
}