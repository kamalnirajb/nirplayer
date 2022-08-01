package niraj.data.hilt

import niraj.data.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (NetworkManager.instance.isOnline()) {
            return chain.proceed(chain.request())
        } else {
            throw IOException("NO_INTERNET")
        }
    }

}