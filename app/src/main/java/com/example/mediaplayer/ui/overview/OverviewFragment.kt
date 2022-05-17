package com.example.mediaplayer.ui.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mediaplayer.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PlayListAdapter(PlayListAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToMusicPlayerFragment(
                        it))
                viewModel.displayWebViewComplete()
            }
        })

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.length > 4) {
                    viewModel.setQuery(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.length > 4) {
                    viewModel.setQuery(query)
                }
                return false
            }
        })

        viewModel.term.observe(viewLifecycleOwner, Observer {
            viewModel.getPlayList()
        })

        return binding.root
    }


}