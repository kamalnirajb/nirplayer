package niraj.data.repository

import niraj.domain.modal.Audio
import niraj.domain.repository.AudioRepository

class AudioListImpl (): AudioRepository {

    val audioList = arrayListOf(
        Audio("Oceansound",
            "oceansound",
            "oceansound",
            14448),
        Audio("Nightlife",
            "nightlife",
            "nightlife",
            15696),
        Audio("Waking Me",
            "waking_me",
            "waking_me",
            13776)

    )

    override suspend fun getAudioList(): List<Audio> = audioList
}