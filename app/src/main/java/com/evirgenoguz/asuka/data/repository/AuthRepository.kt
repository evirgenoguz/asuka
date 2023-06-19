package com.evirgenoguz.asuka.data.repository

import com.evirgenoguz.asuka.data.BaseApiResponse
import com.evirgenoguz.asuka.data.NetworkManager
import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.api.AsukaApi
import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.data.model.response.LoginResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Oguz Evirgen on 19.06.2023.
 */
@ActivityRetainedScoped
class AuthRepository @Inject constructor(
    private val asukaApi: AsukaApi,
): BaseApiResponse() {

    suspend fun login(loginRequest: LoginRequest): Flow<NetworkResult<LoginResponse>> {
        return flow<NetworkResult<LoginResponse>> {
            emit(safeApiCall { asukaApi.login(loginRequest.userName, loginRequest.password) })
        }.flowOn(Dispatchers.IO)
    }

}