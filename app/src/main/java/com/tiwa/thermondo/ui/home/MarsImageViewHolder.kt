package com.tiwa.thermondo.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.tiwa.thermondo.R
import com.tiwa.thermondo.data.model.Photo
import com.tiwa.thermondo.databinding.ItemMarsImageBinding
import com.tiwa.thermondo.extensions.loadImage

open class MarsImageViewHolder(private val binding: ItemMarsImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItems(
        photo: Photo,
        onCardClick: (Int, Int) -> Unit,
        position: Int
    ) {
        binding.imageView.loadImage(photo.img_src)
        binding.textCapturedBy.text =
            binding.root.context.getString(R.string.captured_by, photo.rover.name)
        binding.textCapturedOn.text =
            binding.root.context.getString(R.string.captured_on, photo.earth_date)
        binding.textCamera.text =
            binding.root.context.getString(R.string.camera, photo.camera.name)
        binding.root.setOnClickListener {
            onCardClick.invoke(position, photo.id)
        }
    }

}