package com.evirgenoguz.asuka.core

import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.ServerErrorModel
import retrofit2.Response

/**
 * Created by Oguz Evirgen on 19.06.2023.
 */
abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(ServerErrorModel(message = errorMessage))
}