package com.apoplawski.sporteventsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.apoplawski.sporteventsapp.data.db.LocalEventsDao
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.apoplawski.sporteventsapp.data.network.IDaznEventsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IEventsRepositoryImpl(
    private val daznEventsDataSource: IDaznEventsDataSource,
    private val localEventsDao: LocalEventsDao
) : IEventsRepository {

    private val TAG = "IEventsRepositoryImpl"

    init {
        daznEventsDataSource.apply {
            downloadedEvents.observeForever{ newFetchedEvents ->
                persistFetchedEvents(newFetchedEvents)
                Log.d("$TAG, daznEventsDataSource.downloadedEvents.observeForever{ }", newFetchedEvents.size.toString())
            }
            downloadedSchedule.observeForever { newFetchedSchedule ->
                persistFetchedSchedule(newFetchedSchedule)
                Log.d("$TAG, daznEventsDataSource.downloadedSchedule.observeForever{ }", newFetchedSchedule.size.toString())
            }
        }
    }

    override suspend fun getEvents(): LiveData<List<Event>> {
        return withContext(Dispatchers.IO){
            initEventsData()
            return@withContext daznEventsDataSource.downloadedEvents
        }
    }

    override suspend fun getSchedule(): LiveData<List<ScheduleItem>> {
        return withContext(Dispatchers.IO){
            initScheduleData()
            return@withContext daznEventsDataSource.downloadedSchedule
        }
    }

    private fun persistFetchedEvents(events : List<Event>){
        GlobalScope.launch (Dispatchers.IO){
            localEventsDao.insertAllEvents(events)
            Log.d("$TAG, persistFetchedEvents()", events.size.toString())
        }
    }

    private fun persistFetchedSchedule(schedule : List<ScheduleItem>){
        GlobalScope.launch(Dispatchers.IO) {
            localEventsDao.insertAllSchedule(schedule)
            Log.d("$TAG, persistFetchedSchedule()", schedule.size.toString())
        }
    }

    private suspend fun initEventsData(){
        fetchEvents()
    }

    private suspend fun initScheduleData(){
        fetchSchedule()
    }

    private suspend fun fetchEvents(): LiveData<List<Event>> {
        return withContext(Dispatchers.IO){
            daznEventsDataSource.fetchEvents()
            return@withContext daznEventsDataSource.downloadedEvents
        }
    }

    private suspend fun fetchSchedule(): LiveData<List<ScheduleItem>> {
        return withContext(Dispatchers.IO){
            daznEventsDataSource.fetchSchedule()
            return@withContext daznEventsDataSource.downloadedSchedule
        }
    }

    override suspend fun getLocalSchedule() : LiveData<List<ScheduleItem>>{
        return withContext(Dispatchers.IO){
            return@withContext localEventsDao.getAllSchedule()
        }
    }

    override suspend fun getLocalEvents(): LiveData<List<Event>> {
        return withContext(Dispatchers.IO){
            return@withContext localEventsDao.getAllEvents()
        }
    }
}