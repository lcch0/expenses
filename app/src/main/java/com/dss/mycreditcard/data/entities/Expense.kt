package com.dss.mycreditcard.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expenses")
data class Expense(@PrimaryKey(autoGenerate = true) val id: Int, var amount: Float, var date: Long)
