package com.apoplawski.sporteventsapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.internal.JavaTimeService
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimeServiceTest {

    // Raw events list
    private val mMockInputEventsList : List<Event> = listOf(
        Event("Yesterday", "", "", "2019-11-04T02:07:50.666Z", "", ""),
        Event("Today", "", "", "2019-11-05T02:07:50.666Z", "", ""),
        Event("2 days ago", "", "", "2019-11-03T02:07:50.666Z", "", ""),
        Event("3 days ago", "", "", "2019-11-02T02:07:50.666Z", "", ""),
        Event("4 days ago", "", "", "2019-11-01T02:07:50.666Z", "", "")
    )

    // Formatted events list
    private val mMockFormattedEventsList : List<Event> = listOf(
        Event("Yesterday", "", "", "Yesterday, 02:07", "", ""),
        Event("Today", "", "", "Today, 02:07", "", ""),
        Event("2 days ago", "", "", "2019.11.03", "", ""),
        Event("3 days ago", "", "", "2019.11.02", "", ""),
        Event("4 days ago", "", "", "2019.11.01", "", "")
    )

    // Sorted events list
    private val mMockSortedEventsList : List<Event> = listOf(
        Event("4 days ago", "", "", "2019-11-01T02:07:50.666Z", "", ""),
        Event("3 days ago", "", "", "2019-11-02T02:07:50.666Z", "", ""),
        Event("2 days ago", "", "", "2019-11-03T02:07:50.666Z", "", ""),
        Event("Yesterday", "", "", "2019-11-04T02:07:50.666Z", "", ""),
        Event("Today", "", "", "2019-11-05T02:07:50.666Z", "", "")
    )

    @Test
    @Throws(Exception::class)
    fun getRawEventsAndReturnSorted(){
        val eventsSorted = JavaTimeService.sortEventsByDate(mMockInputEventsList)
        assertThat(eventsSorted, equalTo(mMockSortedEventsList))
    }

    @Test
    @Throws(Exception::class)
    fun getRawYesterdayEventAndReturnFormatted(){
        val yesterdaysEventFormatted = JavaTimeService.processEventDate(mMockInputEventsList[0].date)
        assertThat(yesterdaysEventFormatted, equalTo(mMockFormattedEventsList[0].date))
    }

    @Test
    @Throws(Exception::class)
    fun getRawTodayEventAndReturnFormatted(){
        val todaysEventFormatted = JavaTimeService.processEventDate(mMockInputEventsList[1].date)
        assertThat(todaysEventFormatted, equalTo(mMockFormattedEventsList[1].date))
    }

    @Test
    @Throws(Exception::class)
    fun getRawPastEventsAndReturnFormatted(){
        val twoDaysAgoEventFormatted = JavaTimeService.processEventDate(mMockInputEventsList[2].date)
        val threeDaysAgoEventFormatted = JavaTimeService.processEventDate(mMockInputEventsList[3].date)
        val fourDaysAgoEventFormatted = JavaTimeService.processEventDate(mMockInputEventsList[4].date)

        assertThat(twoDaysAgoEventFormatted, equalTo(mMockFormattedEventsList[2].date))
        assertThat(threeDaysAgoEventFormatted, equalTo(mMockFormattedEventsList[3].date))
        assertThat(fourDaysAgoEventFormatted, equalTo(mMockFormattedEventsList[4].date))
    }
}