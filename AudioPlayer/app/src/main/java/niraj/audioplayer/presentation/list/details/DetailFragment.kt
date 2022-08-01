package niraj.audioplayer.presentation.list.details

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import niraj.audioplayer.R
import niraj.audioplayer.databinding.FragmentDetailBinding
import niraj.audioplayer.databinding.FragmentListBinding
import niraj.audioplayer.presentation.AppEventHandler
import niraj.audioplayer.presentation.list.AudioActivity
import niraj.audioplayer.presentation.list.AudioViewModel

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    val binding: FragmentDetailBinding
        get() = _binding!!

    private val audioDetailViewModel: AudioDetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var audioExoPlayer: ExoPlayer

    // Called on creation of the root view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Initialize the binding variable
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        // return the root view from the binding
        return _binding?.root
    }

    // Called on successful creation of the fragment view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the AppEventHandler to handle the clicks
        binding.detailViewModel = audioDetailViewModel
        binding.audio = args.audio

        with((requireActivity() as AudioActivity)) {
            title = binding.audio?.title
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        prepareMediaPlayer()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    audioDetailViewModel.mutableSharedFlow.collect {

                        audioExoPlayer.seekTo(0, it.durationPlayed)
                        if (audioExoPlayer.playWhenReady != it.isPlaying) {
                            audioExoPlayer.playWhenReady = it.isPlaying
                        }

                        binding.audio = it
                        binding.invalidateAll()
                    }
                }
//                launch {
//                    audioProgress().collect {
//                        withContext(Dispatchers.Main) {
////                            binding.audio?.progress = ((audioExoPlayer.currentPosition.toFloat().div(audioExoPlayer.duration.toFloat())
////                                .times(100)).toInt())
//                        }
//
//                    }
//                }
            }
        }
    }

    private fun prepareMediaPlayer() {

        binding.audio.let {
            val mediaDataSourceFactory: DataSource.Factory =
                DefaultDataSource.Factory(requireContext())

            val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(
                    if (URLUtil.isValidUrl(it?.audio)) MediaItem.fromUri(binding.audio!!.audio) else MediaItem.fromUri(
                        RawResourceDataSource.buildRawResourceUri(
                            resources.getIdentifier(
                                it?.audio,
                                "raw",
                                requireContext().packageName
                            )
                        ).toString()
                    )
                )

            val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

            audioExoPlayer = ExoPlayer.Builder(requireContext())
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

            audioExoPlayer.addListener(object: Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Toast.makeText(requireContext(), "There was an error (${error.errorCodeName}) while playing the audio.", Toast.LENGTH_LONG).show()
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        Player.STATE_IDLE -> {}
                        Player.STATE_BUFFERING -> {
                            binding.pbAudioLoader.visibility = VISIBLE
                        }
                        Player.STATE_READY -> {
                            binding.pbAudioLoader.visibility = GONE
                        }
                        Player.STATE_ENDED -> {
                            Toast.makeText(requireContext(),"Video has ended!",Toast.LENGTH_SHORT).show()
                            activity?.supportFragmentManager?.popBackStack()
                        }
                    }
                }
            })
            audioExoPlayer.addMediaSource(mediaSource)
            audioExoPlayer.seekTo(0, 0L)
            audioExoPlayer.prepare()
        }
    }

//    private fun audioProgress() = flow<Int> {
//        while (binding.audio?.isPlaying!!) {
//            emit(1)
//            delay(100)
//        }
//    }.flowOn(Dispatchers.IO)

    override fun onDestroy() {
        audioExoPlayer.playWhenReady = false
        audioExoPlayer.release()
        super.onDestroy()
    }
}