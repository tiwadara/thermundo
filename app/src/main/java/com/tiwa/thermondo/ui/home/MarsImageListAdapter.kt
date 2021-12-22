package com.tiwa.thermondo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tiwa.thermondo.data.model.Photo
import com.tiwa.thermondo.databinding.ItemMarsImageBinding

class MarsImageListAdapter(
    private val onCardClick: (Int, Int) -> Unit = { _: Int, _: Int -> },
) : ListAdapter<Photo, MarsImageViewHolder>(DIFF_CALLBACK) {

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Photo> = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return newItem == oldItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsImageViewHolder {
        return MarsImageViewHolder(
            ItemMarsImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarsImageViewHolder, position: Int) {
        holder.bindItems(getItem(position), onCardClick, position)
    }
}