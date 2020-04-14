package com.apoplawski.sporteventsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem

@Dao
interface LocalEventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEvents(events : List<Event>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSchedule(schedule : List<ScheduleItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSingleEvent(event : Event)

    @Query ("SELECT * FROM event")
    fun getAllEvents() : LiveData<List<Event>>

    @Query ("SELECT * FROM schedule_item")
    fun getAllSchedule() : LiveData<List<ScheduleItem>>

    @Query("SELECT * FROM event WHERE id LIKE :id")
    fun getEventById(id : String) : Event

    @Query("SELECT * FROM schedule_item WHERE id LIKE :id")
    fun getScheduleItemById(id : String) : ScheduleItem

    @Query ("SELECT * FROM event")
    fun getEventsSnapshot() : List<Event>

    @Query ("SELECT * FROM schedule_item")
    fun getScheduleSnapshot() : List<ScheduleItem>

    @Query("DELETE FROM event")
    fun nukeEventsTable()

    @Query("DELETE FROM schedule_item")
    fun nukeScheduleTable()

}