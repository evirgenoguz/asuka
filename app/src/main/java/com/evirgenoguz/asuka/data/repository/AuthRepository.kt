package com.evirgenoguz.asuka.data.repository

import android.util.Log
import com.evirgenoguz.asuka.core.BaseRepository
import com.evirgenoguz.asuka.data.NetworkManager
import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.api.AsukaApi
import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.data.model.response.LoginResponse
import com.evirgenoguz.asuka.data.model.response.toUiModel
import com.evirgenoguz.asuka.ui.model.LoginUiModel
import com.evirgenoguz.asuka.utils.TokenManager
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Oguz Evirgen on 19.06.2023.
 */
@ActivityRetainedScoped
class AuthRepository @Inject constructor(
    private val asukaApi: AsukaApi,
    private val tokenManager: TokenManager,
    private val networkManager: NetworkManager
) : BaseRepository() {

    //ToDo Add Token manager with shared pref and manage your acces token with token manager

    suspend fun login2(loginRequest: LoginRequest): Flow<NetworkResult<LoginUiModel>> {
        return flow {
            emit(NetworkResult.Loading)
            val result = safeApiCall {
                asukaApi.login(
                    userName = loginRequest.userName,
                    password = loginRequest.password
                )
            }
            Log.d(TAG, "Loading")
            result.onSuccess {
                val uiModel = it.toUiModel()
                tokenManager.saveToken(it.accessToken)
                Log.d(TAG, "Access Token shared pref e kaydedildi ${it.accessToken}")
                emit(NetworkResult.Success(uiModel))
            }.onError {
                emit(NetworkResult.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(loginRequest: LoginRequest): NetworkResult<Response<LoginResponse>> {
        return networkManager.makeRequest {
            asukaApi.login(userName =  loginRequest.userName, password = loginRequest.password)
        }
    }

//    suspend fun isHealthy(): Flow<NetworkResult<String>> {
//        return flow {
//            emit(NetworkResult.Loading)
//            Log.d(TAG, "deneme")
//            val result = safeApiCall {
//                asukaApi.isHealthy()
//            }
//            result.onSuccess {
//                Log.d(TAG, it)
//            }.onError {
//                emit(NetworkResult.Error(it))
//            }
//        }.flowOn(Dispatchers.IO)
//    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}