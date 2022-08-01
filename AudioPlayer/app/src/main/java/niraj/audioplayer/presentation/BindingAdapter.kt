package niraj.audioplayer.presentation

import android.graphics.drawable.Drawable
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import niraj.audioplayer.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun loadRemoteImage(view: ImageView, imageUrl: String ) {
        Glide.with(view.context)
            .load(if (URLUtil.isValidUrl(imageUrl)) imageUrl else view.resources.getIdentifier(imageUrl, "drawable", view.context.packageName))
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .listener(object : RequestListener<Drawable?> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(view)

    }

}