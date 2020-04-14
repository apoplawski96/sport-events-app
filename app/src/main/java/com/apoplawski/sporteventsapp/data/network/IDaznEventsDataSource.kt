package com.apoplawski.sporteventsapp.data.network

import androidx.lifecycle.LiveData
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem

interface IDaznEventsDataSource {
    val downloadedEvents : LiveData<List<Event>>
    val downloadedSchedule : LiveData<List<ScheduleItem>>
    suspend fun fetchEvents()
    suspend fun fetchSchedule()
}