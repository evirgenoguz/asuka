package com.evirgenoguz.asuka.data.model.response

import com.evirgenoguz.asuka.ui.model.LoginUiModel

/**
 * Created by Oguz Evirgen on 19.06.2023.
 */
data class LoginResponse(
    val accessToken: String,
    val tokenType: String
)

fun LoginResponse.toUiModel(): LoginUiModel {
    return LoginUiModel(
        "78hgj"
    )
}