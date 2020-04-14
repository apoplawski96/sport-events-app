package com.apoplawski.sporteventsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem

const val DB_NAME = "events.db"

@Database(entities = [Event::class, ScheduleItem::class], version = 2)
abstract class LocalEventsDatabase : RoomDatabase() {

    abstract fun localEventsDao() : LocalEventsDao

    companion object {
        @Volatile private var instance : LocalEventsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                LocalEventsDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}