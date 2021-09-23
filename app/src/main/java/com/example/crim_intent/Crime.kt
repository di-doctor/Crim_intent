package com.example.crim_intent
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Crime")
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String="",
    var date: Date = Date(),
    var isSolved: Boolean = false)
