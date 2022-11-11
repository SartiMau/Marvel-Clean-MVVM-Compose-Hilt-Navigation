package com.muri.domain.database

import com.muri.domain.entity.Character
import com.muri.domain.utils.Result

interface MarvelRepository {
    fun getDBCharacters(): Result<List<Character>>
    fun getCharacter(id: String): Result<Character>
    fun insertCharactersToDB(charactersList: List<Character>)
    fun insertCharacterToDB(character: Character)
}
