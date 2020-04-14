package com.apoplawski.sporteventsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "schedule_item")
data class ScheduleItem (
    @PrimaryKey
    val id : String,
    val title : String,
    val subtitle : String,
    val date : String,
    val imageUrl : String
)