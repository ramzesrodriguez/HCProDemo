package com.externalpods.hcprodemo.data.datasources

import com.externalpods.hcprodemo.data.remote.datasources.UserRemoteDataSource
import com.externalpods.hcprodemo.data.remote.responses.UsersResponse
import com.externalpods.hcprodemo.data.remote.services.UserApiServices
import com.externalpods.hcprodemo.data.remote.utils.ApiResponse
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest {

  @MockK
  private lateinit var userApiServices: UserApiServices

  private lateinit var dataSource: UserDataSource

  @Before
  fun setup() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    dataSource = UserRemoteDataSource(userApiServices)
  }

  @Test
  fun `validate get all users success`() = runTest {
    coEvery { userApiServices.getAllUsers() } returns Response.success(UsersResponse())

    val data = dataSource.getAllUsers()
    Truth.assertThat(data).isInstanceOf(ApiResponse.Success::class.java)

    coVerify { userApiServices.getAllUsers() }
  }

  @Test
  fun `validate get all users error`() = runTest {
    coEvery { userApiServices.getAllUsers() } returns Response.error(
      401,
      ResponseBody.create(MediaType.parse("application/json"), "ERROR")
    )

    val data = dataSource.getAllUsers()
    Truth.assertThat(data).isInstanceOf(ApiResponse.Error::class.java)

    val error = (data as ApiResponse.Error).errorMessage
    Truth.assertThat(error).isNotEmpty()

    coVerify { userApiServices.getAllUsers() }
  }
}