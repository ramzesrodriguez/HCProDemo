package com.externalpods.hcprodemo.data.remote.services

import com.externalpods.hcprodemo.data.remote.responses.UsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiServices {
  @GET("users")
  suspend fun getAllUsers(): Response<UsersResponse>
}