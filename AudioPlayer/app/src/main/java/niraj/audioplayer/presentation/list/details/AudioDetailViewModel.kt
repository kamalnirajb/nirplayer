package niraj.audioplayer.presentation.list.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import niraj.domain.modal.Audio
import javax.inject.Inject

@HiltViewModel
class AudioDetailViewModel @Inject constructor(private val application: Application): ViewModel() {

    val mutableSharedFlow = MutableSharedFlow<Audio>(replay = 0)

    fun onAudioItemClick(audio: Audio) {
        viewModelScope.launch {
            audio.isPlaying = !audio.isPlaying
            mutableSharedFlow.emit(audio)
        }
    }

    fun onFavouriteItemClick(audio: Audio) {
        viewModelScope.launch {
            val sharedPref =
                application.applicationContext.getSharedPreferences("audio", Context.MODE_PRIVATE) ?: return@launch
            with(sharedPref.edit()) {
                audio.isFavourite = !audio.isFavourite
                putString("favourite",if (audio.isFavourite) audio.audio else "")
                apply()
            }

            mutableSharedFlow.emit(audio)
        }
    }

    fun onRatingClicked(rating: Float, audio: Audio) {
        viewModelScope.launch {
            audio.rating = rating
            val sharedPref =
                application.applicationContext.getSharedPreferences("audio", Context.MODE_PRIVATE) ?: return@launch
            with(sharedPref.edit()) {
                putFloat(audio.title , audio.rating)
                apply()
            }
            mutableSharedFlow.emit(audio)
        }
    }

    fun onProgressChanged(progress: Long, audio: Audio) {
        viewModelScope.launch {
            audio.durationPlayed = progress
            audio.timeToDisplayForAudio = ((audio.durationPlayed / 1000) / 60).toString() + ":" + ((audio.durationPlayed / 1000) % 60).toString() + "/" + audio.durationTotal
            mutableSharedFlow.emit(audio)
        }
    }
}