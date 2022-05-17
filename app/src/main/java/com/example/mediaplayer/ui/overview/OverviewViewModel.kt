package com.example.mediaplayer.ui.overview

import androidx.lifecycle.*
import com.example.mediaplayer.data.model.Property
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.data.network.MusicApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

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

    fun getPlayList() {
        val query = term.value.toString()
        MusicApi.retrofitService
            .getSearchProperty(query)
            .toObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Property> {
                override fun onSubscribe(d: Disposable) {
                    _status.postValue(NetworkStatus.LOADING)
                }

                override fun onNext(t: Property) {
                    _properties.postValue(t.results)
                }

                override fun onError(e: Throwable) {
                    _status.postValue(NetworkStatus.ERROR)
                    _properties.postValue(ArrayList())
                }

                override fun onComplete() {
                    _status.postValue(NetworkStatus.DONE)
                }
            })
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

