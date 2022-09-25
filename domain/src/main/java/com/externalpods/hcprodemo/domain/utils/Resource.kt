package com.externalpods.hcprodemo.domain.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<out T> {
  class Success<T>(val data: T) : Resource<T>()
  class Error(val exception: Throwable) : Resource<Nothing>()
  class Loading<T>(val data: T? = null) : Resource<T>()
}