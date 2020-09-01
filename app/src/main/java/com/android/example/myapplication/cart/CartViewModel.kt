/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.android.example.myapplication.cart


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.myapplication.album.AlbumFilterType
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.data.Result
import com.android.example.myapplication.repository.AlbumRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: AlbumRepository) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val cartDataLoading: LiveData<Boolean> = _dataLoading
    private val _items = MutableLiveData<List<Album>>().apply { value = emptyList() }
    val items: LiveData<List<Album>> = _items

    init {
        fetchCartItems()
    }

    fun fetchCartItems() {
        viewModelScope.launch {
            _dataLoading.value = true
            val albumsResult = repository.getAlbums(AlbumFilterType.CART_ITEMS)

            if (albumsResult is Result.Success) {
                var albums = albumsResult.data
                _items.value = ArrayList(albums)
            } else {
                _items.value = emptyList()
            }
            _dataLoading.value = false
        }
    }

}
