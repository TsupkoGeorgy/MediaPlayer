package com.example.mediaplayer.ui.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.mediaplayer.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {

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
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToMusicPlayerFragment(it))
                viewModel.displayWebViewComplete()
            }
        })

        return binding.root
    }


}