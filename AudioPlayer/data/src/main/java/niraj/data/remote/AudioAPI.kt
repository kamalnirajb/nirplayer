package niraj.data.remote

import niraj.data.modal.AudioListDTO
import retrofit2.http.GET

interface AudioAPI {
    @GET("Learnfield-GmbH/CodingChallenge/master/react%20native/simple%20audio%20player/data/manifest.json")
    suspend fun getAudioList(): AudioListDTO
}