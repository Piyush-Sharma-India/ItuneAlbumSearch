package com.android.example.myapplication.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.databinding.AlbumCartItemBinding

class AlbumCartAdapter : RecyclerView.Adapter<AlbumCartAdapter.ViewHolder>() {

    private var albumList = ArrayList<Album>()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: AlbumCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album) {
            binding.album = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlbumCartItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    fun getItem(position: Int) = albumList[position]


    override fun getItemCount(): Int {
        return albumList.size
    }

    fun submitList(items: List<Album>) {
        albumList.clear()
        albumList.addAll(items)
        notifyDataSetChanged()
    }
}

