package com.example.mediaplayer.ui.overview

import androidx.lifecycle.*
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


    private val _term = MutableLiveData<String>()
    val term: LiveData<String>
        get() = _term

    fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                if (term.value.toString() != "") {
                    val response = MusicApi.retrofitService.getSearchProperty(term.value.toString())
                    _properties.value = response.results
                }
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun setQuery(query: String) {
        _term.value = query
    }

    fun displayPropertyDetails(result: Result) {
        _navigateToSelectedProperty.value = result
    }

    fun displayWebViewComplete() {
        _navigateToSelectedProperty.value = null
    }
}

