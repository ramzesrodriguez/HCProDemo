package com.externalpods.hcprodemo.domain.usecases

import com.example.domain.qualifiers.IoDispatcher
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.repositories.UsersRepository
import com.externalpods.hcprodemo.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
  private val repository: UsersRepository,
  @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<UserDto>>(coroutineDispatcher) {

  override fun buildRequest(params: Unit?): Flow<Resource<List<UserDto>>> {
    return repository.getAllUsers()
  }
}