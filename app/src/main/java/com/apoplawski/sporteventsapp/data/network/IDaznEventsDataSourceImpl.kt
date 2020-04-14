package com.apoplawski.sporteventsapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem

class IDaznEventsDataSourceImpl(
    private val daznEventsApiService: DaznEventsApiService
) : IDaznEventsDataSource {

    private val TAG = "IDaznEventsDataSource"

    private val _downloadedEvents = MutableLiveData<List<Event>>()
    private val _downloadedSchedule = MutableLiveData<List<ScheduleItem>>()

    override val downloadedEvents : LiveData<List<Event>>
        get() = _downloadedEvents

    override val downloadedSchedule: LiveData<List<ScheduleItem>>
        get() = _downloadedSchedule

    override suspend fun fetchEvents() {
        val fetchedEvents = daznEventsApiService.getEvents().await()
        _downloadedEvents.postValue(fetchedEvents)
        Log.d("$TAG - fetchEvents()", fetchedEvents.size.toString())
    }

    override suspend fun fetchSchedule() {
        val fetchedSchedule = daznEventsApiService.getSchedule().await()
        _downloadedSchedule.postValue(fetchedSchedule)
        Log.d("$TAG - fetchSchedule()", fetchedSchedule.size.toString())
    }

}