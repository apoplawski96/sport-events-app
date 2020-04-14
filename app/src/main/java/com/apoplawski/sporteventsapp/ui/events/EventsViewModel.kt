package com.apoplawski.sporteventsapp.ui.events

import android.util.Log
import androidx.lifecycle.*
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.repository.IEventsRepository
import kotlinx.coroutines.launch

class EventsViewModel() : ViewModel() {

    private val TAG = "EventsViewModel"

    private lateinit var mAppRepository : IEventsRepository

    private val _eventsOutputData = MediatorLiveData<List<Event>>()

    constructor(eventsRepository: IEventsRepository) : this(){
        mAppRepository = eventsRepository
    }

    val events : LiveData<List<Event>> = liveData{
        val data = mAppRepository.getEvents()
        emit(data.value!!)
    }

    fun observeOnEvents(owner : LifecycleOwner, observer : Observer<List<Event>>) = viewModelScope.launch{
        Log.d(TAG, "- observeOnEvents() called")
        _eventsOutputData.observe(owner, observer)

        _eventsOutputData.addSource(mAppRepository.getLocalEvents()) { localEventsFetched ->
            if (localEventsFetched == null) return@addSource
            _eventsOutputData.postValue(localEventsFetched)
        }
    }
}
