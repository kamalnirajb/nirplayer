package niraj.domain.usecases

import niraj.domain.modal.Audio
import niraj.domain.repository.AudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import niraj.domain.common.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AudioListUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    operator fun invoke(): Flow<Resource<List<Audio>>> = flow {
        try {
            emit(Resource.Loading())
            val response = audioRepository.getAudioList()
            val list = if(response.isEmpty()) emptyList() else response
            emit(Resource.Success(data = list))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}