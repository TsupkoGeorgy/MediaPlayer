package com.example.mediaplayer.ui.music_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.model.Result

class MusicPlayerViewModelFactory(private val selectedResult:Result) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicPlayerViewModel::class.java)) {
            return MusicPlayerViewModel(selectedResult) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
