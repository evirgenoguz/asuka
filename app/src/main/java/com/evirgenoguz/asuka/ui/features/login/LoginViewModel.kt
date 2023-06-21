package com.evirgenoguz.asuka.ui.features.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evirgenoguz.asuka.core.BaseViewModel
import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.data.model.response.LoginResponse
import com.evirgenoguz.asuka.data.repository.AuthRepository
import com.evirgenoguz.asuka.ui.model.LoginUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _login = MutableLiveData<NetworkResult<LoginUiModel>>()
    val login: LiveData<NetworkResult<LoginUiModel>> = _login

    private val _healthy = MutableLiveData<NetworkResult<String>>()
    val healthy: LiveData<NetworkResult<String>> = _healthy




    fun login(loginRequest: LoginRequest) =
        viewModelScope.launch {
            //we don't have addOnSuccessListener or addOnFailureListener
            authRepository.login(loginRequest).collect{
                //Todo in here will change when i learn
                _login.postValue(it)
            }
        }

    fun isHealthy() {
        viewModelScope.launch {
            authRepository.isHealthy().collect{
                _healthy.postValue(it)
            }
        }
    }

    //    fun fetchDogResponse() = viewModelScope.launch {
//        repository.getDog().collect { values ->
//            _response.value = values
//        }

}