package com.externalpods.hcprodemo.data.remote.datasources

import com.externalpods.hcprodemo.data.datasources.UserDataSource
import com.externalpods.hcprodemo.data.entities.UserEntity
import com.externalpods.hcprodemo.data.remote.entities.UserRemoteEntity
import com.externalpods.hcprodemo.data.remote.services.UserApiServices
import com.externalpods.hcprodemo.data.remote.utils.ApiResponse
import com.externalpods.hcprodemo.domain.utils.Mapper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val userServices: UserApiServices,
  private val userEntityMapper: Mapper<UserRemoteEntity, UserEntity>
) : UserDataSource {

  override suspend fun getAllUsers(): ApiResponse<List<UserEntity>> {
    return try {
      val response = userServices.getAllUsers()
      if (response.isSuccessful && response.body() != null) {
        val data = response.body() ?: emptyList()
        ApiResponse.Success(userEntityMapper.mapCollection(data))
      } else {
        ApiResponse.Error(response.message())
      }
    } catch (e: Throwable) {
      ApiResponse.Error(e.message ?: "UNKNOWN ERROR")
    }
  }

}