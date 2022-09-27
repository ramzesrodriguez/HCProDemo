package com.externalpods.hcprodemo.presentation.di

import com.externalpods.hcprodemo.data.entities.UserEntity
import com.externalpods.hcprodemo.data.mappers.UserEntityMapper
import com.externalpods.hcprodemo.data.remote.entities.UserRemoteEntity
import com.externalpods.hcprodemo.data.remote.mappers.UserResponseMapper
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.utils.Mapper
import com.externalpods.hcprodemo.presentation.users.list.mappers.UsersModelMapper
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

  @Binds
  abstract fun bindsUserResponseMapper(mapper: UserResponseMapper): Mapper<UserRemoteEntity, UserEntity>

  @Binds
  abstract fun bindsUserEntityMapper(mapper: UserEntityMapper): Mapper<UserEntity, UserDto>

  @Binds
  abstract fun bindsUserModelMapper(mapper: UsersModelMapper): Mapper<UserDto, UserModel>
}