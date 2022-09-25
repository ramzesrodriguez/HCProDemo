package com.externalpods.hcprodemo.presentation.di

import com.externalpods.hcprodemo.data.remote.services.UserApiServices
import com.externalpods.hcprodemo.presentation.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val MAX_IDLE_CONNECTIONS = 5
  private const val KEEP_ALIVE_DURATION_SECONDS = 10L

  /**
   * Provides [UserApiServices] instance for queries
   */
  @Provides
  @Singleton
  fun provideApigeeService(retrofit: Retrofit): UserApiServices {
    return retrofit.create(UserApiServices::class.java)
  }

  /**
   * Provides [Retrofit] instance for Base API rest service
   */
  @Provides
  fun provideApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_API_ENDPOINT)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  /**
   * Provides [OkHttpClient] instance
   */
  @Provides
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().run {
      connectTimeout(30, TimeUnit.SECONDS)
      writeTimeout(30, TimeUnit.SECONDS)
      readTimeout(30, TimeUnit.SECONDS)
      connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
      retryOnConnectionFailure(false)
      connectionPool(
        ConnectionPool(
          MAX_IDLE_CONNECTIONS,
          KEEP_ALIVE_DURATION_SECONDS,
          TimeUnit.SECONDS
        )
      )
      build()
    }
  }
}