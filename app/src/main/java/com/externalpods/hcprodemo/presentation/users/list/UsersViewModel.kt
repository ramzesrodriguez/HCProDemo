package com.externalpods.hcprodemo.presentation.users.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.states.UiEffect
import com.externalpods.hcprodemo.domain.states.UiEvent
import com.externalpods.hcprodemo.domain.states.UiState
import com.externalpods.hcprodemo.domain.usecases.GetAllUsersUseCase
import com.externalpods.hcprodemo.domain.utils.Mapper
import com.externalpods.hcprodemo.domain.utils.Resource
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import com.externalpods.hcprodemo.presentation.users.list.states.UsersContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
  private val getAllUsersUseCase: GetAllUsersUseCase,
  private val userMapper: Mapper<UserDto, UserModel>
) : ViewModel() {

  private val statesFlows = mutableListOf<MutableStateFlow<UiState>>()

  private val _event = MutableSharedFlow<UiEvent>()
  private val event = _event.asSharedFlow()

  private val _effect: Channel<UiEffect> = Channel()
  val effect = _effect.receiveAsFlow()

  init {
    getStates().forEach {
      statesFlows.add(MutableStateFlow(it))
    }
    subscribeToEvents()
  }

  private fun getStates(): List<UiState> {
    return listOf(UsersContract.UserState.GetUsersLoading)
  }

  private fun subscribeToEvents() {
    viewModelScope.launch {
      event.collect {
        handleEvent(it)
      }
    }
  }

  private fun handleEvent(event: UiEvent) {
    when (event) {
      is UsersContract.UserEvent.GetAllUsers -> {
        getAllUsers()
      }
    }
  }

  private fun getAllUsers() {
    viewModelScope.launch {
      getAllUsersUseCase.execute(null)
        .onStart {
          setState(UsersContract.UserState.GetUsersLoading)
        }
        .collect {
        when (val resource = it) {
          is Resource.Success -> {
            val data = userMapper.mapCollection(resource.data)
            setState(UsersContract.UserState.GetUsersSuccess(data))
          }
          is Resource.Error -> {
            setEffect { UsersContract.UserEffect.GetUsersError }
          }
          is Resource.Loading -> {
            setState(UsersContract.UserState.GetUsersLoading)
          }
        }
      }
    }
  }

  private fun setState(state: UiState) {
    val oldState = statesFlows.find { it.value.javaClass.superclass == state.javaClass.superclass }
    oldState?.value = state
  }

  /**
   * Set new Event
   */
  fun setEvent(event: UiEvent) {
    val newEvent = event
    viewModelScope.launch {
      _event.emit(newEvent)
    }
  }

  /**
   * Set new Effect
   */
  private fun setEffect(builder: () -> UiEffect) {
    val effectValue = builder()
    viewModelScope.launch { _effect.send(effectValue) }
  }

  fun getStatesList() = statesFlows.toList()

  inline fun <reified S: UiState> getStateFlow(): StateFlow<S>? {
    return getStatesList().firstOrNull { it.value is S }?.let {
      @Suppress("UNCHECKED_CAST")
      it as StateFlow<S>
    } ?:run { null }
  }
}