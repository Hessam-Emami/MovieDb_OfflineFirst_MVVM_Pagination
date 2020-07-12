package com.emami.moviedb.movie.util

import android.content.Context
import com.emami.moviedb.R
import com.emami.moviedb.common.util.NoConnectivityException
import retrofit2.HttpException
import java.io.IOError
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionLocalizer @Inject constructor() {
    fun getExceptionMessage(exception: Exception, context: Context): String = with(context) {
        return@with when (exception) {
            is NoConnectivityException -> getString(
                R.string.error_no_internet
            )
            is SocketTimeoutException -> getString(R.string.error_timeout)
            is IOError -> getString(R.string.error_io)
            is HttpException -> getString(R.string.error_no_internet)
            else -> exception.localizedMessage ?: exception.message
            ?: getString(R.string.error_unknown)
        }
    }
}