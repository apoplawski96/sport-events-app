package com.apoplawski.sporteventsapp.data.repository

import androidx.lifecycle.LiveData
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem

interface IEventsRepository {
    suspend fun getEvents() : LiveData<List<Event>>
    suspend fun getSchedule() : LiveData<List<ScheduleItem>>
    suspend fun getLocalEvents() : LiveData<List<Event>>
    suspend fun getLocalSchedule() : LiveData<List<ScheduleItem>>
}