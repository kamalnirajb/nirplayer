package niraj.domain.repository

import niraj.domain.modal.Audio


interface AudioRepository {

    suspend fun getAudioList(): List<Audio>

}