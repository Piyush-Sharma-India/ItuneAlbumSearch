package com.android.example.myapplication.album.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.android.example.myapplication.album.AlbumSearchViewModel
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.databinding.AlbumItemBinding

class AlbumSearchAdapter(private val viewModel: AlbumSearchViewModel) :
    RecyclerView.Adapter<AlbumSearchAdapter.ViewHolder>(), Filterable {

    private var albumList = ArrayList<Album>()
    private var albumListFiltered = ArrayList<Album>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: AlbumSearchViewModel, item: Album) {

            binding.viewModel = viewModel
            binding.album = item
            binding.shoppingcartCheckBox.isChecked = item.isAddedToCart
            binding.shoppingcartCheckBox.setOnClickListener {
                item.isAddedToCart = !item.isAddedToCart
                viewModel.addToCart(album = item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlbumItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    fun getItem(position: Int) = albumListFiltered[position]


    override fun getItemCount(): Int {
        return albumListFiltered.size
    }

    fun submitList(items: List<Album>) {
        albumList.clear()
        albumList.addAll(items)
        albumListFiltered.clear()
        albumListFiltered.addAll(items)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    albumListFiltered = albumList
                } else {
                    val resultList = ArrayList<Album>()
                    for (album in albumListFiltered) {
                        if (album.trackName.toLowerCase().contains(charSearch.toLowerCase())
                            || album.artistName.toLowerCase().contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(album)
                        }
                    }
                    albumListFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = albumListFiltered
                return filterResults
            }

            override fun publishResults(p0: CharSequence, filterResults: FilterResults) {
                albumListFiltered = filterResults.values as ArrayList<Album>
                notifyDataSetChanged()
            }
        }
    }
}

