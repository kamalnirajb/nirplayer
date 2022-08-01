package niraj.data.mapper

import niraj.data.modal.AudioDTO
import niraj.data.modal.AudioListDTO
import niraj.domain.modal.Audio
import javax.inject.Inject

open class AudioListMapper @Inject constructor(): Mapper<AudioListDTO, List<Audio>>{
    override fun toModal(type: AudioListDTO): List<Audio> {
        return type.audioList.map {
            Audio(title = it.title, audio = it.audio, cover = it.cover, totalDurationMs = it.totalDurationMs)
        }
    }

    override fun fromModal(type: List<Audio>): AudioListDTO {
        return AudioListDTO(type.map {
            AudioDTO(title = it.title, audio = it.audio, cover = it.cover, totalDurationMs = it.totalDurationMs)
        })
    }

}