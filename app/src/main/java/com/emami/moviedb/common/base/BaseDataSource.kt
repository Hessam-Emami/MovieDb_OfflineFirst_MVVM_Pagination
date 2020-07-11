package com.emami.moviedb.common.base

import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.common.util.NoConnectivityException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException
import java.net.SocketTimeoutException

@Suppress("UNCHECKED_CAST")
open class BaseDataSource() {
    /**
     * Used to invoke network requests, fetching the data and returning the state whether it was
     * successful of not
     */
    suspend fun <T : Any> invokeApi(call: suspend () -> Response<T>): DataResult<T> {
        return try {
            val result = call.invoke()
            if (result.isSuccessful) {
                DataResult.Success(result.body() ?: Any() as T)
            } else {
                Timber.i("Api call error ${result.raw()}")
                DataResult.Error(
                    RuntimeException(
                        result.errorBody()?.toString() ?: result.message()
                    )
                )
            }
        } catch (e: SocketTimeoutException) {
            return DataResult.Error(e)
        } catch (e: NoConnectivityException) {
            return DataResult.Error(e)
        } catch (e: IOException) {
            return DataResult.Error(e)
        } catch (t: Throwable) {
            //Serious problem, log this into firebase
            Timber.e("Api Call throwable: $t")
            throw t
        }
    }

}