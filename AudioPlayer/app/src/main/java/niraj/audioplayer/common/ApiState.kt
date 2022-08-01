package niraj.audioplayer.common

import niraj.domain.modal.Audio

data class ApiState(
    val data:List<Audio>? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
