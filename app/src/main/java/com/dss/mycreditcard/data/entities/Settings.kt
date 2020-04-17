package com.dss.mycreditcard.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "settings")
data class Settings(@PrimaryKey(autoGenerate = true) val id: Int,
                    var periodType: Int = PeriodType.Monthly.value,
                    var date1: Long = Date().time,
                    var date2: Long = Date().time,
                    var softLimit: Float = 0f,
                    var hardLimit: Float = 0f)
