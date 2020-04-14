package com.apoplawski.sporteventsapp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apoplawski.sporteventsapp.data.repository.IEventsRepository

class ScheduleViewModelFactory(
    private val eventsRepository : IEventsRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleViewModel(eventsRepository) as T
    }
}