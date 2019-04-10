package kitttn.voiassignment.views.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kitttn.api.entities.Artist
import kitttn.api.entities.Image
import kitttn.voiassignment.databinding.DataBindingArtistBinding

@BindingAdapter("imageUrl")
fun loadBestImage(imageView: ImageView, images: List<Image>) {
    val viewSize = imageView.width
    val (image, _) = images
        .map { it to Math.abs(it.width - viewSize) }
        .minBy { (_, diffSize) -> diffSize } ?: return

    Picasso.get()
        .load(image.url)
        .into(imageView)
}

class ArtistViewHolder(private val binding: DataBindingArtistBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Artist) {
        binding.artist = item
        binding.executePendingBindings()
    }
}

class ArtistAdapter(private val items: List<Artist> = mutableListOf()) : RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = DataBindingArtistBinding.inflate(LayoutInflater.from(parent.context))
        return ArtistViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}