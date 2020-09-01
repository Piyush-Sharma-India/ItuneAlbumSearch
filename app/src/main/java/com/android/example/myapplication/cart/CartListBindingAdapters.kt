package com.android.example.myapplication.cart

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.myapplication.album.adapter.AlbumCartAdapter
import com.android.example.myapplication.data.Album

@BindingAdapter("cart_items")
fun setItems(listView: RecyclerView, items: List<Album>) {
    (listView.adapter as? AlbumCartAdapter)?.submitList(items)
}