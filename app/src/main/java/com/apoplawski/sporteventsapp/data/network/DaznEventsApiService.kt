package com.apoplawski.sporteventsapp.data.network

import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = ""

interface DaznEventsApiService {

    @GET("getEvents")
    fun getEvents() : Deferred<List<Event>>

    @GET("getSchedule")
    fun getSchedule() : Deferred<List<ScheduleItem>>

    companion object {
        operator fun invoke() : DaznEventsApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(DaznEventsApiService::class.java)
        }
    }
}