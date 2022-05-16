package com.example.mediaplayer.ui.overview

import androidx.lifecycle.*
import com.example.mediaplayer.data.model.Property
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.data.network.MusicApi
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
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
        //_status.value = NetworkStatus.LOADING
        val query = term.value.toString()
        MusicApi.retrofitService
            .getSearchProperty(query)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Property> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Property) {
                    _properties.value = t.results
                }

                override fun onError(e: Throwable) {
                    _status.value = NetworkStatus.ERROR
                    _properties.value = ArrayList()
                }

                override fun onComplete() {
                    _status.value = NetworkStatus.DONE
                }

            })

        _properties.value
//        viewModelScope.launch {
//            _status.value = NetworkStatus.LOADING
//            try {
//                if (term.value.toString() != "") {
//                    val coroutineSearch = MusicApi.retrofitService.coroutineSearch(query)
//                    _properties.value = coroutineSearch.results
//                }
//                _status.value = NetworkStatus.DONE
//            } catch (e: Exception) {
//                _status.value = NetworkStatus.ERROR
//                _properties.value = ArrayList()
//            }
//        }
       // val coroutineSearch = MusicApi.retrofitService.coroutineSearch(query)
//        override fun onSubscribe(d: Disposable) {
//            _status.value = NetworkStatus.LOADING
//        }
//
//        override fun onNext(t: List<Result>) {
//            _properties.value = t
//
//        }
//
//        override fun onError(e: Throwable) {
//            _status.value = NetworkStatus.ERROR
//            _properties.value = ArrayList()
//        }
//
//        override fun onComplete() {
//            _status.value = NetworkStatus.DONE
//        }

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

