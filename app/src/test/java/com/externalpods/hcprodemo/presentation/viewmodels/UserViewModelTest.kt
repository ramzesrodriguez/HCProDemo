package com.externalpods.hcprodemo.presentation.viewmodels

import app.cash.turbine.test
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.usecases.GetAllUsersUseCase
import com.externalpods.hcprodemo.domain.utils.Resource
import com.externalpods.hcprodemo.presentation.users.list.viewmodel.UsersViewModel
import com.externalpods.hcprodemo.presentation.users.list.mappers.UsersModelMapper
import com.externalpods.hcprodemo.presentation.users.list.states.UsersContract
import com.externalpods.hcprodemo.presentation.utils.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class UserViewModelTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @MockK
  private lateinit var getAllUsersUseCase: GetAllUsersUseCase
  private val usersMapper = UsersModelMapper()

  private lateinit var viewModel: UsersViewModel

  @Before
  fun setup() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    viewModel = UsersViewModel(getAllUsersUseCase, usersMapper)
  }

  @Test
  fun `validate get all users is success`() = runTest {
    val list = listOf(UserDto(id = 1, name = "test"))
    coEvery { getAllUsersUseCase.execute(null) } returns flowOf(
      Resource.Success(list)
    )

    viewModel.getStateFlow<UsersContract.UserState>()?.test {
      // Expect Resource.Idle from initial state
      Truth.assertThat(awaitItem()).isEqualTo(
        UsersContract.UserState.GetUsersIdle
      )
      // Set - GetAllUsersEvent
      viewModel.setEvent(UsersContract.UserEvent.GetAllUsers)
      // Expect Resource.Loading
      Truth.assertThat(awaitItem()).isEqualTo(
        UsersContract.UserState.GetUsersLoading
      )
      // Expect Resource.Success
      val expected = awaitItem()
      val expectedData = (expected as UsersContract.UserState.GetUsersSuccess).users
      Truth.assertThat(expectedData).isNotEmpty()
      Truth.assertThat(expectedData).containsExactlyElementsIn(usersMapper.mapCollection(list))
      // Cancel and ignore remaining
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun `validate get all users is error contains message`() = runTest {
    coEvery { getAllUsersUseCase.execute(null) } returns flowOf(
      Resource.Error(Throwable("UNKNOWN_ERROR"))
    )

    viewModel.getStateFlow<UsersContract.UserState>()?.test {
      // Expect Resource.Idle from initial state
      Truth.assertThat(awaitItem()).isEqualTo(
        UsersContract.UserState.GetUsersIdle
      )
      // Set - GetAllUsersEvent
      viewModel.setEvent(UsersContract.UserEvent.GetAllUsers)
      // Expect Resource.Loading
      Truth.assertThat(awaitItem()).isEqualTo(
        UsersContract.UserState.GetUsersLoading
      )
      // Cancel and ignore remaining
      cancelAndIgnoreRemainingEvents()
    }

    viewModel.effect.test {
      val expected = awaitItem()
      Truth.assertThat(expected).isInstanceOf(UsersContract.UserEffect.GetUsersError::class.java)

      val expectedData = (expected as UsersContract.UserEffect.GetUsersError).error
      Truth.assertThat(expectedData).isInstanceOf(Throwable::class.java)
      Truth.assertThat(expectedData.message).isNotEmpty()

      // Cancel and ignore remaining
      cancelAndIgnoreRemainingEvents()
    }

    coVerify { getAllUsersUseCase.execute(null) }
  }
}