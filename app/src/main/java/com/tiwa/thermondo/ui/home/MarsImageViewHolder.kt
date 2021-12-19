package com.tiwa.thermondo.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.thermondo.data.model.Photo
import com.tiwa.thermondo.databinding.ListItemLinksBinding

open class MarsImageViewHolder(private val binding: ListItemLinksBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItems(
        photo: Photo,
        onCardClick: (Int, Int) -> Unit,
        position: Int,
        context: Context
    ) {
        binding.textOldLink.text = photo.img_src
        binding.textNewLink.text = photo.rover.name
        binding.root.setOnClickListener {
            onCardClick.invoke(position, photo.id)
        }
    }

}