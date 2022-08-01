package niraj.data.modal

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class AudioDTO(

    @NonNull
    @SerializedName("title")
    val title: String,

    @NonNull
    @SerializedName("audio")
    val audio: String,

    @NonNull
    @SerializedName("cover")
    val cover: String,

    @NonNull
    @SerializedName("totalDurationMs")
    val totalDurationMs: Long
)
