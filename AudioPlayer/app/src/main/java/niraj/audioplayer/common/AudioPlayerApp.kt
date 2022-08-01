package niraj.audioplayer.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import niraj.data.hilt.NetworkManager

@HiltAndroidApp
class AudioPlayerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkManager.instance.setupNetworkManager(applicationContext)
    }
}