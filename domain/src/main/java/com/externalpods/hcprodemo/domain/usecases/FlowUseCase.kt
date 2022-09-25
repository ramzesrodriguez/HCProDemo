package com.externalpods.hcprodemo.domain.usecases

import com.externalpods.hcprodemo.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Base Use Case class
 */
abstract class FlowUseCase<Params, Model>(
  private val dispatcher: CoroutineDispatcher
) {

  abstract fun buildRequest(params: Params?): Flow<Resource<Model>>

  val defaultParamError = flow { emit(Resource.Error(Throwable("Param cannot be null"))) }

  suspend fun execute(params: Params?): Flow<Resource<Model>> {
    return try {
      buildRequest(params).flowOn(dispatcher)
    } catch (exception: Exception) {
      flow { emit(Resource.Error(exception)) }
    }
  }
}