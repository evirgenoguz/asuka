package com.evirgenoguz.asuka.data

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.evirgenoguz.asuka.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Provider

/**
 * Created by Oguz Evirgen on 10.04.2023.
 */
class NetworkManager(
    private val application: Context,
    private val gsonProvider: Provider<Gson>
) {
    private companion object {
        val TAG: String = NetworkManager::class.java.simpleName
    }

    /**
     * Retrieves the GSON object. Used as a provider to
     * eliminate performance issues when the object is creating.
     */
    private val gson: Gson
        get() = gsonProvider.get()


    /**
     * The dispatcher to be used when making requests.
     */
    private val dispatcher = Dispatchers.IO

   suspend fun <T> makeRequest(
       apiCall: suspend NetworkManager.() -> T
   ): NetworkResult<T> {
       return withContext(dispatcher){
           try {
               NetworkResult.Success(apiCall(this@NetworkManager))
           } catch (ioException : IOException) {
               Log.d(TAG, "Network Connection Failed.")
               NetworkResult.Error(
                   ServerErrorModel(message = stringResource(R.string.not_connected_try_again))
               )
           } catch (exception: Exception) {
               val errorMessage = exception.localizedMessage ?: exception.message ?: stringResource(R.string.general_error_message)
               NetworkResult.Error(
                   ServerErrorModel(message = errorMessage)
               )
           }
       }
   }


    /**
     * To retrieve strings from resource id with help of
     * injected application context.
     */
    private fun stringResource(@StringRes resId: Int) = application.getString(resId)
}