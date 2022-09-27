package com.externalpods.hcprodemo.data.repositories

import com.externalpods.hcprodemo.data.datasources.UserDataSource
import com.externalpods.hcprodemo.data.entities.UserEntity
import com.externalpods.hcprodemo.data.remote.entities.UserRemoteEntity
import com.externalpods.hcprodemo.data.remote.utils.ApiResponse
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.repositories.UsersRepository
import com.externalpods.hcprodemo.domain.utils.Mapper
import com.externalpods.hcprodemo.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(
  private val remoteDataSource: UserDataSource,
  private val userMapper: Mapper<UserEntity, UserDto>
) : UsersRepository {

  override fun getAllUsers(): Flow<Resource<List<UserDto>>> = flow {
    when (val response = remoteDataSource.getAllUsers()) {
      is ApiResponse.Success -> {
        val result = userMapper.mapCollection(response.data)
        emit(Resource.Success(result))
      }
      is ApiResponse.Error -> {
        emit(Resource.Error(Throwable(response.errorMessage)))
      }
    }
  }
}