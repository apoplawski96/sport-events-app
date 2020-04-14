package com.apoplawski.sporteventsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apoplawski.sporteventsapp.data.db.LocalEventsDao
import com.apoplawski.sporteventsapp.data.db.LocalEventsDatabase
import com.apoplawski.sporteventsapp.data.model.Event
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao : LocalEventsDao
    private lateinit var db : LocalEventsDatabase

    private val mMockEventsList : List<Event> = listOf(
        Event("1", "Liverpool VS AC Milan", "Champions League Final", "2005", "imgUrl", "vidUrl"),
        Event("2", "Chelsea VS Man United", "Champions League Final", "2009", "imgUrl", "vidUrl"))

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LocalEventsDatabase::class.java).build()
        dao = db.localEventsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() { db.close() }

    @Test
    @Throws(Exception::class)
    fun insertEventAndReadInList(){
        dao.insertAllEvents(mMockEventsList)
        assertThat(dao.getEventsSnapshot(), equalTo(mMockEventsList))
    }

    @Test
    @Throws(Exception::class)
    fun insertSingleEventAndRead(){
        dao.addSingleEvent(mMockEventsList[0])
        val eventById = dao.getEventById(mMockEventsList[0].id)
        assertThat(eventById, equalTo(mMockEventsList[0]))
    }

}