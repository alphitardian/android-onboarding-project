package com.alphitardian.onboardingproject.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSource as AuthDataSource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.RemoteDataSourceImpl as AuthDataSourceImpl
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSource as UserRemoteDataSource
import com.alphitardian.onboardingproject.data.user.data_source.remote.RemoteDataSourceImpl as UserDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindUserRemoteDataSource(dataSource: UserDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun bindAuthRemoteDataSource(dataSource: AuthDataSourceImpl): AuthDataSource
}