package com.evirgenoguz.asuka.data.api

import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.data.model.response.LoginResponse
import com.evirgenoguz.asuka.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Oguz Evirgen on 10.04.2023.
 */
interface AsukaApi {

    //ToDo: this block will have some functions with @GET @POST like that also it has to be suspend function
    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}