package com.example.mediaplayer.ui.music_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.model.Result

class MusicPlayerViewModel(private val selectedResult: Result) : ViewModel() {

    private val _selectedProperty = MutableLiveData<Result>()
    val selectedProperty: LiveData<Result>
        get() = _selectedProperty

    init {
        _selectedProperty.value = selectedResult
    }
}