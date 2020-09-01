package com.android.example.myapplication.album

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.example.myapplication.R
import com.android.example.myapplication.album.adapter.AlbumSearchAdapter
import com.android.example.myapplication.data.Album
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    val circularProgress = CircularProgressDrawable(imageView.context)
    circularProgress.strokeWidth = 5f
    circularProgress.centerRadius = 30f
    circularProgress.start()

    Glide.with(imageView.context)
        .asBitmap()
        .placeholder(circularProgress)
        .error(R.drawable.ic_launcher_background)
        .load(url)
        .override(100, 100) // Can be 2000, 2000
        .into(imageView)
        .waitForLayout()
}

@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Album>) {
    (listView.adapter as? AlbumSearchAdapter)?.submitList(items)
}