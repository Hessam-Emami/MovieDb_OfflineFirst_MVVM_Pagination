package com.emami.moviedb.common.util

import okhttp3.Request

object HeaderQueryUtil {
    fun addSecretQueryParams(request: Request, appId: String): Request {
        val newUrl =
            request.url.newBuilder().addQueryParameter(HEADER_APP_ID, appId).build()
        return request.newBuilder().url(newUrl).build()
    }

    private const val HEADER_APP_ID = "api_key"

}

