package com.apoplawski.sporteventsapp

import android.app.Application
import com.apoplawski.sporteventsapp.data.db.LocalEventsDatabase
import com.apoplawski.sporteventsapp.data.network.DaznEventsApiService
import com.apoplawski.sporteventsapp.data.network.IDaznEventsDataSource
import com.apoplawski.sporteventsapp.data.network.IDaznEventsDataSourceImpl
import com.apoplawski.sporteventsapp.data.repository.IEventsRepository
import com.apoplawski.sporteventsapp.data.repository.IEventsRepositoryImpl
import com.apoplawski.sporteventsapp.ui.events.EventsViewModelFactory
import com.apoplawski.sporteventsapp.ui.schedule.ScheduleViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SportEventsApplication : Application(), KodeinAware {
    override val kodein : Kodein = Kodein.lazy {
        import(androidXModule(this@SportEventsApplication))
        bind() from singleton { LocalEventsDatabase(instance()) }
        bind() from singleton { instance<LocalEventsDatabase>().localEventsDao() }
        bind() from singleton { DaznEventsApiService() }
        bind<IDaznEventsDataSource>() with singleton { IDaznEventsDataSourceImpl(instance()) }
        bind<IEventsRepository>() with singleton { IEventsRepositoryImpl(instance(), instance()) }
        bind() from provider { EventsViewModelFactory(instance()) }
        bind() from provider { ScheduleViewModelFactory(instance()) }
    }
}