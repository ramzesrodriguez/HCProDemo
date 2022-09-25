package com.externalpods.hcprodemo.data.remote.utils

sealed class ApiResponse<out R> {
  data class Success<out T>(val data: T) : ApiResponse<T>()
  data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}