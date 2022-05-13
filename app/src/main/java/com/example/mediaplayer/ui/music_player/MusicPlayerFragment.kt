package com.example.mediaplayer.ui.music_player

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.MusicPlayerFragmentBinding
import com.example.mediaplayer.databinding.OverviewFragmentBinding

class MusicPlayerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = MusicPlayerFragmentBinding.inflate(inflater)

        val selectedResult = MusicPlayerFragmentArgs.fromBundle(arguments!!).resultItem
        val viewModelFactory =  MusicPlayerViewModelFactory(selectedResult)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(MusicPlayerViewModel::class.java)
        return binding.root
    }



}