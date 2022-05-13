package com.example.mediaplayer.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.data.network.MusicApi
import kotlinx.coroutines.launch


enum class NetworkStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus>
        get() = _status

    private val _properties = MutableLiveData<List<Result>>()
    val properties: LiveData<List<Result>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<Result?>()
    val navigateToSelectedProperty: LiveData<Result?>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                val response = MusicApi.retrofitService.getSearchProperty("Sixteen")
                _properties.value = response.results
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun displayPropertyDetails(result: Result) {
        _navigateToSelectedProperty.value = result
    }

    fun displayWebViewComplete() {
        _navigateToSelectedProperty.value = null
    }
}

