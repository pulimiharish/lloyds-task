package com.lbg.data.repository

import javax.inject.Inject
import retrofit2.Response

class ApiResponseHandler @Inject constructor() {

    inline fun <T> handleApiResponse(
        response: Response<T>,
        onSuccess: (T) -> Unit,
        onError: () -> Unit
    ) {
        if (response.isSuccessful) {
            response.body()?.let { onSuccess(it) }
        } else {
            onError()
        }
    }
}
