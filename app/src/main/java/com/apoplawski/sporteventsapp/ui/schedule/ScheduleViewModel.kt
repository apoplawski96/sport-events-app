package com.apoplawski.sporteventsapp.ui.schedule

import android.util.Log
import androidx.lifecycle.*
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.apoplawski.sporteventsapp.data.repository.IEventsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ScheduleViewModel() : ViewModel() {

    private val TAG = "ScheduleViewModel"

    private lateinit var mAppRepository : IEventsRepository

    private val _scheduleOutputData = MediatorLiveData<List<ScheduleItem>>()

    private val _schedule = MutableLiveData<List<ScheduleItem>>()

    val schedule : LiveData<List<ScheduleItem>>
        get() = _schedule

    constructor(eventsRepository: IEventsRepository) : this(){
        mAppRepository = eventsRepository
        viewModelScope.launch {
            initScheduleData()
            scheduleRefreshData()
        }
    }

    private fun initScheduleData() = viewModelScope.launch {
        val data = mAppRepository.getSchedule()
        _schedule.postValue(data.value)
    }

    private fun scheduleRefreshData() = viewModelScope.launch{
        while (isActive){
            delay(300000)
            initScheduleData()
        }
    }

    fun observeOnSchedule(owner : LifecycleOwner, observer : Observer<List<ScheduleItem>>) = viewModelScope.launch{
        Log.d(TAG, "observeOnSchedule() called")
        _scheduleOutputData.observe(owner, observer)

        _scheduleOutputData.addSource(mAppRepository.getLocalSchedule()) { localScheduleFetched ->
            if (localScheduleFetched == null) return@addSource
            _scheduleOutputData.postValue(localScheduleFetched)
        }
    }
}
