package com.example.mediaplayer.ui.overview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val binding: OverviewFragmentBinding =   DataBindingUtil.inflate(
            inflater,
            R.layout.overview_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        binding.viewModel = viewModel


        binding.photosGrid.adapter = PlayListAdapter(PlayListAdapter.OnClickListener {

        })




        return binding.root
    }


}