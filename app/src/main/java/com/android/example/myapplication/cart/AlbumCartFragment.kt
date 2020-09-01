package com.android.example.myapplication.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.example.myapplication.AlbumApplication
import com.android.example.myapplication.R
import com.android.example.myapplication.album.adapter.AlbumCartAdapter
import com.android.example.myapplication.databinding.AlbumCartFragmentBinding
import javax.inject.Inject

class AlbumCartFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel by viewModels<CartViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: AlbumCartFragmentBinding

    private lateinit var listAdapter: AlbumCartAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as AlbumApplication).appComponent.cartComponent().create()
            .inject(this)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true);
            val icon = ContextCompat.getDrawable(
                this@AlbumCartFragment.requireContext(),
                R.drawable.ic_baseline_arrow_back_24
            );
            setHomeAsUpIndicator(icon);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AlbumCartFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = cartViewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            listAdapter = AlbumCartAdapter()
            viewDataBinding.albumCartList.adapter = listAdapter
        }
    }
}