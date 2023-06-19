package com.evirgenoguz.asuka.ui.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evirgenoguz.asuka.core.BaseViewModel
import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.data.model.response.LoginResponse
import com.evirgenoguz.asuka.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _login = MutableLiveData<NetworkResult<LoginResponse>>()
    val login: LiveData<NetworkResult<LoginResponse>> = _login


    fun login(loginRequest: LoginRequest) =
        viewModelScope.launch {
            _login.postValue(NetworkResult.Loading)
            //we don't have addOnSuccessListener or addOnFailureListener
            authRepository.login(loginRequest).collect{
                //Todo in here will change when i learn
                _login.postValue(it)
            }
        }



    //    fun fetchDogResponse() = viewModelScope.launch {
//        repository.getDog().collect { values ->
//            _response.value = values
//        }

}