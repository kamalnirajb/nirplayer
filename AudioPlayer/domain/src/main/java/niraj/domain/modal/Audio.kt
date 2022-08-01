package niraj.domain.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audio(
    val title: String,
    val audio: String,
    val cover: String,
    val totalDurationMs: Long,
    var durationPlayed: Long = 0,
    var durationTotal: String? = "",
    var timeToDisplayForAudio: String? = "",
    var progress: Int = 0,
    var isFavourite: Boolean = false,
    var isPlaying: Boolean = false,
    var rating: Float = 0.0f
): Parcelable