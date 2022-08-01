package niraj.audioplayer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import niraj.audioplayer.common.ApiState
import niraj.domain.common.Resource
import niraj.domain.modal.Audio
import niraj.domain.usecases.AudioListUseCase
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(private val audioListUseCase: AudioListUseCase): ViewModel() {

    private val _audioList = MutableStateFlow(ApiState())

    val audioList: StateFlow<ApiState> = _audioList

    fun getAudioList() {
        audioListUseCase().onEach { result ->
            _audioList.value = when(result){
                is Resource.Loading-> ApiState(isLoading = true)
                is Resource.Error-> ApiState(isLoading = false, error = result.message?:"")
                is Resource.Success-> ApiState(isLoading = false, data = result.data)
            }
        }.launchIn(viewModelScope)
    }
}