package com.tinyfight.weather.widget.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Create at 2022/5/30
 * @author Yao
 * Name com.tinyfight.gweather.data.network.NetworkHelper
 */

const val CODE_UNKNOWN = "UNKNOWN"
const val FAIL_MESSAGE = "MESSAGE_FAIL"

data class ApiError(
    val code: String? = null,
    val msg: String? = null,
)

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Fail(val error: ApiError?) : Result<Nothing>()
}

suspend inline fun <reified T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline apiCall: suspend () -> T?,
): Result<T> {
    return withContext(dispatcher) {
        try {
            val result = apiCall.invoke()
            if (result != null) {
                Result.Success(result)
            } else {
                val error = ApiError(CODE_UNKNOWN, FAIL_MESSAGE)
                Result.Fail(error)
            }
        } catch (cancel: CancellationException) {
            Result.Fail(ApiError(CODE_UNKNOWN, cancel.message))
        } catch (ex: HttpException) {
            Result.Fail(ApiError(ex.code().toString(), ex.message()))
        } catch (ex: Exception) {
            Result.Fail(ApiError(CODE_UNKNOWN, ex.message))
        }
    }
}


suspend inline fun <reified T : Any> safeFlowApiCall(
    crossinline apiCall: suspend () -> T?,
): Flow<T> {
    return flow {
        val response =
            apiCall.invoke() ?: throw IllegalArgumentException("IllegalArgumentException")
        emit(response)
    }.flowOn(Dispatchers.IO)
}
