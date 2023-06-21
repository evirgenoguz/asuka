package com.evirgenoguz.asuka.utils

import android.content.Context
import com.evirgenoguz.asuka.utils.Constants.PREFS_TOKEN_FILE
import com.evirgenoguz.asuka.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Oguz Evirgen on 20.06.2023.
 */
class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, getBearerToken(token)).apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    private fun getBearerToken(accessToken: String) =
        "$BEARER $accessToken"


    companion object{
        private const val BEARER = "Bearer"
    }
}