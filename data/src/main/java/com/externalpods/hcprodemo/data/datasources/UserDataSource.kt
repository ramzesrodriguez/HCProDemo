package com.externalpods.hcprodemo.data.datasources

import com.externalpods.hcprodemo.data.remote.entities.UserEntity
import com.externalpods.hcprodemo.data.remote.utils.ApiResponse

interface UserDataSource {
  suspend fun getAllUsers(): ApiResponse<List<UserEntity>>
}