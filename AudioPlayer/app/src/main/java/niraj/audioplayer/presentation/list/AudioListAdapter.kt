package niraj.audioplayer.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import niraj.audioplayer.databinding.LayoutAudioItemBinding
import niraj.audioplayer.presentation.AppEventHandler
import niraj.domain.modal.Audio

class AudioListAdapter:  RecyclerView.Adapter<AudioListAdapter.AudioViewHolder>() {

    private var listener: AppEventHandler? = null
    var audios = mutableListOf<Audio>()
    class AudioViewHolder(val viewHolder: LayoutAudioItemBinding): RecyclerView.ViewHolder(viewHolder.root)

    fun setContentList(audios: MutableList<Audio>) {
        this.audios = audios
        notifyDataSetChanged()
    }

    fun setClickListener(appEventHandler: AppEventHandler) {
        this.listener = appEventHandler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val binding = LayoutAudioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AudioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.viewHolder.audio = this.audios.get(position)
        holder.viewHolder.handler = listener
    }

    override fun getItemCount(): Int {
        return this.audios.size
    }
}