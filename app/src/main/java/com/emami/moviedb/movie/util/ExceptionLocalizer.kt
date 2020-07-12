package com.emami.moviedb.movie.util

import android.content.Context
import com.emami.moviedb.R
import com.emami.moviedb.common.util.NoConnectivityException
import java.io.IOError
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionLocalizer @Inject constructor() {
    fun getExceptionMessage(exception: Exception, context: Context): String = with(context){
        return@with when (exception) {
            is NoConnectivityException -> getString(
                R.string.error_no_internet
            )
            is SocketTimeoutException -> getString(R.string.error_timeout)
            is IOError -> getString(R.string.error_io)
            else -> exception.localizedMessage ?: exception.message
            ?: getString(R.string.error_unknown)
        }
    }
}