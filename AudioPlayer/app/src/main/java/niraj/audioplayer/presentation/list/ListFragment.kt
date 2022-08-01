package niraj.audioplayer.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import niraj.audioplayer.R
import niraj.audioplayer.databinding.FragmentListBinding
import niraj.audioplayer.presentation.AppEventHandler

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    val binding: FragmentListBinding
    get() = _binding!!

    private val audioViewModel: AudioViewModel by activityViewModels()

    private var audioListAdapter = AudioListAdapter()


    // Called on creation of the root view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Initialize the binding variable
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // return the root view from the binding
        return _binding?.root
    }

    // Called on successful creation of the fragment view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the AppEventHandler to handle the clicks
        val appEventHandler = AppEventHandler(findNavController())

        // Add the adapter to the recyclerview
        binding.rcvListAudio.apply {
            adapter = audioListAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            audioViewModel.getAudioList()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                audioViewModel.audioList.collect {

                    binding.isLoading = it.isLoading
                    binding.hasError = !it.isLoading && !it.error.isEmpty()
                    binding.errorMessage = it.error

                    it.data.let { audios ->
                        when {
                            audios.isNullOrEmpty() -> binding.errorMessage = getString(R.string.no_data_found)
                            else -> {
                                audioListAdapter.setClickListener(appEventHandler)
                                audioListAdapter.setContentList(audios.toMutableList())
                            }
                        }
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        with((requireActivity() as AudioActivity)) {
            title = getString(R.string.audio_list)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        audioViewModel.getAudioList()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}