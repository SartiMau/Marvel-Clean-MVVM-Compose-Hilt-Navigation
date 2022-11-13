package com.muri.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marvel_characters")
class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val img: String
)
