package com.android.example.myapplication.album

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.myapplication.AlbumApplication
import com.android.example.myapplication.R
import com.android.example.myapplication.album.adapter.AlbumSearchAdapter
import com.android.example.myapplication.databinding.AlbumSerachFragmentBinding
import javax.inject.Inject

class AlbumSearchFragment : Fragment() {

    private var mMenu: Menu? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val albumViewModel by viewModels<AlbumSearchViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: AlbumSerachFragmentBinding

    private lateinit var listAdapter: AlbumSearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as AlbumApplication).appComponent.albumComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AlbumSerachFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = albumViewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        setupListAdapter()
        setupSearchView()
        observeDataLoading()
    }

    private fun setupSearchView() {

        viewDataBinding.albumSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText);
                return false;
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {

            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            R.id.menu_cart -> {
                navigateToCart()
                true
            }
            else -> false
        }

    private fun navigateToCart() {
        val action = AlbumSearchFragmentDirections.actionAlbumSearchFragmentDestToCartFragment()
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_album_fragment_menu, menu)
        mMenu = menu
    }

    private fun toggleMenuEnable(isEnabled: Boolean) {
        mMenu?.let { menu ->
            for (menuItem in menu) {
                menuItem.setEnabled(isEnabled)
            }
            activity?.invalidateOptionsMenu()
        }
    }

    private fun observeDataLoading() {
        albumViewModel.dataLoading.observe(viewLifecycleOwner, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                t?.let { isDataLoading ->
                    toggleMenuEnable(!isDataLoading)
                    toggleSearchViewInputType(isDataLoading)
                }
            }

        })
    }

    private fun toggleSearchViewInputType(isDataLoading: Boolean) {
        viewDataBinding.albumSearchView.inputType = if (isDataLoading) InputType.TYPE_NULL else
            InputType.TYPE_CLASS_TEXT
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_albums, menu)

            setOnMenuItemClickListener {
                albumViewModel.setFiltering(
                    when (it.itemId) {
                        R.id.release_date -> AlbumFilterType.RELEASE_DATE
                        R.id.artist_name -> AlbumFilterType.ARTIST_NAME
                        R.id.track_name -> AlbumFilterType.TRACK_NAME
                        else -> AlbumFilterType.COLLECTION_NAME
                    }
                )
                albumViewModel.fetchAlbum()
                true
            }
            show()
        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            listAdapter = AlbumSearchAdapter(viewModel)
            viewDataBinding.albumSearchList.adapter = listAdapter
        }
    }
}