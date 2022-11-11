package com.muri.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muri.data.database.entity.CharacterEntity

@Dao
interface MarvelDao {
    @Query("SELECT * FROM marvel_characters")
    fun getDBCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM marvel_characters WHERE id = :id")
    fun getCharacter(id: String): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)
}
