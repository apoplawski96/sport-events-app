package com.apoplawski.sporteventsapp.internal

import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.data.model.ScheduleItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors

const val FULL_DATE_TIME_PATTERN : String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val JUST_DATE_PATTERN : String = "yyyy.MM.dd"
const val JUST_TIME_PATTERN : String = "HH:mm"

object JavaTimeService {

    private val inputFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern(FULL_DATE_TIME_PATTERN)
    private val shortDateFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern(JUST_DATE_PATTERN)
    private val shortTimeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern(JUST_TIME_PATTERN)

    fun processEventDate(inputTimeString : String) : String {
        val eventDateTime = LocalDateTime.parse(inputTimeString, inputFormatter)
        val daysBetween = getDaysBetweenNowAndEvent(eventDateTime)

        return when (daysBetween){
            0 -> "Today, ${eventDateTime.format(shortTimeFormatter)}"
            -1 -> "Yesterday, ${eventDateTime.format(shortTimeFormatter)}"
            else -> eventDateTime.format(shortDateFormatter).toString()
        }
    }

    fun processScheduleDate(inputTimeString : String) : String {
        val eventDateTime = LocalDateTime.parse(inputTimeString, inputFormatter)
        val daysBetween = getDaysBetweenNowAndEvent(eventDateTime)

        return when (daysBetween){
            0 -> "Today, ${eventDateTime.format(shortTimeFormatter)}"
            1 -> "Tomorrow, ${eventDateTime.format(shortTimeFormatter)}"
            else -> "In $daysBetween days"
        }
    }

    private fun getDaysBetweenNowAndEvent(eventDateTime : LocalDateTime) : Int {
        val timeNow : LocalDateTime = LocalDateTime.now()
        return ChronoUnit.DAYS.between(timeNow, eventDateTime).toInt()
    }

    fun sortEventsByDate(inputList : List<Event>) : List<Event>{
        return inputList.stream().sorted(Comparator.comparing (Event::date)).collect(Collectors.toList())
    }

    fun filterAndSortEventsByDate(inputList : List<ScheduleItem>) : List<ScheduleItem>{
        val todaysList : MutableList<ScheduleItem> = mutableListOf()

        for (item in inputList){
            val eventDateTime = LocalDateTime.parse(item.date, inputFormatter)
            val daysBetween = getDaysBetweenNowAndEvent(eventDateTime)
            if (daysBetween == 0) todaysList.add(item)
        }

        return todaysList.stream().sorted(Comparator.comparing (ScheduleItem::date)).collect(Collectors.toList())
    }
}