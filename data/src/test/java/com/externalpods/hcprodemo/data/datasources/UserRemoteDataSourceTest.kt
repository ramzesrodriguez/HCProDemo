package com.externalpods.hcprodemo.data.datasources

import com.externalpods.hcprodemo.data.remote.datasources.UserRemoteDataSource
import com.externalpods.hcprodemo.data.remote.mappers.UserResponseMapper
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
import okhttp3.Protocol
import okhttp3.Request
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

  private val userEntityMapper = UserResponseMapper()

  private lateinit var dataSource: UserDataSource

  @Before
  fun setup() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    dataSource = UserRemoteDataSource(userApiServices, userEntityMapper)
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
    val errorMessage = "ERROR"
    val response = createHttpErrorResponse(errorMessage)
    coEvery { userApiServices.getAllUsers() } returns response

    val data = dataSource.getAllUsers()
    Truth.assertThat(data).isInstanceOf(ApiResponse.Error::class.java)

    val error = (data as ApiResponse.Error).errorMessage
    Truth.assertThat(error).isNotEmpty()
    Truth.assertThat(error).isEqualTo(errorMessage)

    coVerify { userApiServices.getAllUsers() }
  }

  private fun createHttpErrorResponse(message: String): Response<UsersResponse> {
    val responseBody = ResponseBody.create(MediaType.parse("application/json"), message)
    val requestBuilder = Request.Builder()
      .get()
      .url("http://localhost")
      .build()
    val response = okhttp3.Response.Builder()
      .body(responseBody)
      .protocol(Protocol.HTTP_1_1)
      .code(400)
      .message(message)
      .request(requestBuilder)
      .build()
    return Response.error(
      responseBody,
      response
    )
  }
}