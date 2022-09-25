package com.externalpods.hcprodemo.presentation.users.list.states

import com.externalpods.hcprodemo.domain.states.UiEffect
import com.externalpods.hcprodemo.domain.states.UiEvent
import com.externalpods.hcprodemo.domain.states.UiState
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel

sealed class UsersContract {
  sealed class UserEvent: UiEvent {
    object GetAllUsers: UserEvent()
  }

  sealed class UserState: UiState {
    object GetUsersIdle: UserState()
    object GetUsersLoading: UserState()
    data class GetUsersSuccess(val users: List<UserModel>): UserState()
  }

  sealed class UserEffect: UiEffect {
    data class GetUsersError(val error: Throwable): UserEffect()
  }
}