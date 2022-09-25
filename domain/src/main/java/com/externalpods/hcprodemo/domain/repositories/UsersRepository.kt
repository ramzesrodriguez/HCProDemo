package com.externalpods.hcprodemo.domain.repositories

import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
  fun getAllUsers(): Flow<Resource<List<UserDto>>>
}