package com.emami.moviedb.common.base

import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.common.util.NoConnectivityException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
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
                DataResult.Error(result.errorBody()?.toString() ?: result.message())
            }
        } catch (e: SocketTimeoutException) {
            return DataResult.Error(ERROR_TIMEOUT, e)
        } catch (e: NoConnectivityException) {
            return DataResult.Error(ERROR_INTERNET, e)
        } catch (e: IOException) {
            return DataResult.Error(ERROR_IO, e)
        } catch (t: Throwable) {
            Timber.e("Api Call throwable: $t")
            throw t
        }
    }

    //Should get from string.xml and injected into BaseDataSource, but
    //I've added them here for now for the sake of simplicity
    private companion object {
        const val ERROR_TIMEOUT = "This takes longer than usual, please try again in a bit"
        const val ERROR_INTERNET = "Please check your internet and try again"
        const val ERROR_IO = "There is a problem in getting response from the server"
    }
}