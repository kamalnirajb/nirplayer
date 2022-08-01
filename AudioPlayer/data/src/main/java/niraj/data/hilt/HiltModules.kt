package niraj.data.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import niraj.data.mapper.AudioListMapper
import niraj.data.remote.AudioAPI
import niraj.data.repository.AudioListImpl
import niraj.domain.repository.AudioRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideAudioAPI(): AudioAPI {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ConnectionInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AudioAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAudioListRepository(@ApplicationContext context: Context, audioAPI: AudioAPI, audioListMapper: AudioListMapper): AudioRepository {
        return AudioListImpl(context, audioAPI, audioListMapper)
    }

}