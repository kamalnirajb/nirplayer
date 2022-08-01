package niraj.data.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import niraj.data.repository.AudioListImpl
import niraj.domain.repository.AudioRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideAudioListRepository(): AudioRepository = AudioListImpl()
}