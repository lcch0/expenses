package com.dss.mycreditcard.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sessions")
data class Session(@PrimaryKey(autoGenerate = true) val id: Int,
                    var date1: Long = Date().time,
                    var date2: Long = Date().time,
                    var amount: Float = 0f,
                    var softLimit: Float = 0f,
                    var hardLimit: Float = 0f)