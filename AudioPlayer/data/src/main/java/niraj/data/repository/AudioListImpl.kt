package niraj.data.repository

import android.content.Context
import androidx.core.util.TimeUtils
import niraj.data.hilt.NetworkManager
import niraj.data.remote.AudioAPI
import niraj.data.mapper.AudioListMapper
import niraj.data.modal.AudioDTO
import niraj.data.modal.AudioListDTO
import niraj.domain.modal.Audio
import niraj.domain.repository.AudioRepository

class AudioListImpl (private val context: Context, private val audioApi: AudioAPI, private val audioListMapper: AudioListMapper): AudioRepository {
    // Get the audio list
    override suspend fun getAudioList(): List<Audio> {
        val audioList = (when (NetworkManager.instance.isOnline()) {
            true -> audioListMapper.toModal(audioApi.getAudioList())
            false -> audioListMapper.toModal(
                AudioListDTO(
                    listOf(
                        AudioDTO("Oceansound",
                            "oceansound",
                            "oceansound",
                            14448
                        ),
                        AudioDTO("Nightlife",
                            "nightlife",
                            "nightlife",
                            15696
                        ),
                        AudioDTO("Waking Me",
                            "waking_me",
                            "waking_me",
                            13776
                        )
                    )
                )
            )
        }).toMutableList()

        val sharedpref = context.getSharedPreferences("audio", Context.MODE_PRIVATE)
        val favouriteAudio = sharedpref.getString("favourite", "")
        audioList.map {
            with(it) {
                isFavourite = (!favouriteAudio.isNullOrEmpty()) && audio.equals(favouriteAudio)
                rating = sharedpref.getFloat(title, 0.0f)
                durationPlayed = 0
                durationTotal = ((it.totalDurationMs / 1000) / 60).toString() + ":" + ((it.totalDurationMs / 1000) % 60).toString()
                timeToDisplayForAudio = "0:00/$durationTotal"
            }
        }
        return audioList.toList()
    }
}