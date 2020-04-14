package com.apoplawski.sporteventsapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apoplawski.sporteventsapp.data.repository.IEventsRepository

class EventsViewModelFactory(
    private val eventsRepository : IEventsRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventsViewModel(eventsRepository) as T
    }
}