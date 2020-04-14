package com.apoplawski.sporteventsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity (tableName = "event")
@Parcelize
data class Event (
    @PrimaryKey
    val id : String,
    val title : String,
    val subtitle : String,
    val date : String,
    val imageUrl : String,
    val videoUrl : String
) : Parcelable