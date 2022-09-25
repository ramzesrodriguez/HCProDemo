package com.externalpods.hcprodemo.presentation.di

import com.externalpods.hcprodemo.data.datasources.UserDataSource
import com.externalpods.hcprodemo.data.remote.datasources.UserRemoteDataSource
import com.externalpods.hcprodemo.data.repositories.UserDataRepository
import com.externalpods.hcprodemo.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserApiSource(userRemoteDataSource: UserRemoteDataSource): UserDataSource

    @Binds
    @ViewModelScoped
    abstract fun newsRepository(dataRepository: UserDataRepository): UsersRepository
}