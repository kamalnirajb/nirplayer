package niraj.data.modal

import com.google.gson.annotations.SerializedName

data class AudioListDTO(

    @SerializedName("data")
    val audioList: List<AudioDTO>
)
