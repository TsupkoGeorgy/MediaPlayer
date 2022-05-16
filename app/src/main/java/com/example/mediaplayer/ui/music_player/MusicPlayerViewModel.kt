package com.example.mediaplayer.ui.music_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.model.Result

enum class PlayStatus { PLAY, PAUSE }

class MusicPlayerViewModel(private val selectedResult: Result) : ViewModel() {

    private val _status = MutableLiveData<PlayStatus>(PlayStatus.PAUSE)
    val status: LiveData<PlayStatus>
        get() = _status

    private val _selectedProperty = MutableLiveData<Result>()
    val selectedProperty: LiveData<Result>
        get() = _selectedProperty

    init {
        _selectedProperty.value = selectedResult
    }

    fun clickPlayButton() {
        when (_status.value) {
            PlayStatus.PLAY -> _status.value = PlayStatus.PAUSE
            PlayStatus.PAUSE -> _status.value = PlayStatus.PLAY
        }
    }
}