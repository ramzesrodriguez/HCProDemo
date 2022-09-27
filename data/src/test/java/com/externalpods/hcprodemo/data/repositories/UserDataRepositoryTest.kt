package com.externalpods.hcprodemo.data.repositories

import app.cash.turbine.test
import com.externalpods.hcprodemo.data.datasources.UserDataSource
import com.externalpods.hcprodemo.data.entities.UserEntity
import com.externalpods.hcprodemo.data.mappers.UserEntityMapper
import com.externalpods.hcprodemo.data.remote.utils.ApiResponse
import com.externalpods.hcprodemo.domain.repositories.UsersRepository
import com.externalpods.hcprodemo.domain.utils.Resource
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class UserDataRepositoryTest {

  @MockK
  private lateinit var remoteDataSource: UserDataSource
  private val userMapper = UserEntityMapper()

  private lateinit var usersRepository: UsersRepository

  @Before
  fun setup() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    usersRepository = UserDataRepository(remoteDataSource, userMapper)
  }

  @Test
  fun `validate get all users success`() = runTest {
    val list = listOf(UserEntity(id = 0, name = "test"))
    coEvery { remoteDataSource.getAllUsers() } returns ApiResponse.Success(list)

    usersRepository.getAllUsers().test {
      val expected = awaitItem()
      Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)

      cancelAndIgnoreRemainingEvents()
    }

    coVerify { remoteDataSource.getAllUsers() }
  }

  @Test
  fun `validate get all users error`() = runTest {
    coEvery { remoteDataSource.getAllUsers() } returns ApiResponse.Error(
      "UNKNOWN_ERROR"
    )

    usersRepository.getAllUsers().test {
      val expected = awaitItem()
      Truth.assertThat(expected).isInstanceOf(Resource.Error::class.java)

      val data = (expected as Resource.Error).exception
      Truth.assertThat(data).isInstanceOf(Throwable::class.java)

      cancelAndIgnoreRemainingEvents()
    }

    coVerify { remoteDataSource.getAllUsers() }
  }
}