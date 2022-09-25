package com.externalpods.hrprodemo.domain.usecases

import app.cash.turbine.test
import com.externalpods.hrprodemo.domain.utils.MainCoroutineRule
import com.externalpods.hcprodemo.domain.dtos.UserDto
import com.externalpods.hcprodemo.domain.repositories.UsersRepository
import com.externalpods.hcprodemo.domain.usecases.GetAllUsersUseCase
import com.externalpods.hcprodemo.domain.utils.Resource
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
class GetAllUsersUseCaseTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @MockK
  private lateinit var usersRepository: UsersRepository

  private lateinit var useCase: GetAllUsersUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    useCase = GetAllUsersUseCase(usersRepository, mainCoroutineRule.dispatcher)
  }

  @Test
  fun `validate get all users return success`() = runTest {
    val list = listOf(UserDto(id = 1, name = "test"))
    coEvery { usersRepository.getAllUsers() } returns flowOf(Resource.Success(list))

    useCase.execute(null).test {
      val expected = awaitItem()
      Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)

      val data = (expected as Resource.Success).data
      Truth.assertThat(data).isNotEmpty()
      Truth.assertThat(data).containsExactlyElementsIn(list)

      cancelAndIgnoreRemainingEvents()
    }

    coVerify { usersRepository.getAllUsers() }
  }

  @Test
  fun `validate get all users return error`() = runTest {
    coEvery { usersRepository.getAllUsers() } returns flowOf(Resource.Error(Throwable("UNKNOWN_ERROR")))

    useCase.execute(null).test {
      val expected = awaitItem()
      Truth.assertThat(expected).isInstanceOf(Resource.Error::class.java)

      val data = (expected as Resource.Error).exception
      Truth.assertThat(data).isInstanceOf(Throwable::class.java)

      cancelAndIgnoreRemainingEvents()
    }

    coVerify { usersRepository.getAllUsers() }
  }
}