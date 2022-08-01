package niraj.audioplayer.presentation

import android.content.Context
import androidx.navigation.NavController
import niraj.audioplayer.R
import niraj.audioplayer.presentation.list.ListFragmentDirections
import niraj.domain.modal.Audio


open class AppEventHandler (private val navCtrl: NavController){

    /**
     * Called on click of any particular item
     */
    fun onLaunch(audio: Audio) {
        try {
            navCtrl.navigate(ListFragmentDirections.actionLaunchDetail(audio))
        }catch (e: Exception) {
         // Ignore exception
        }
    }
}