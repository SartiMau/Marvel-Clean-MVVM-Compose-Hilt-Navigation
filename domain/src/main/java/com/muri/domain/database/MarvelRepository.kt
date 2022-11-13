package com.muri.domain.database

import com.muri.domain.entity.Character
import com.muri.domain.utils.CoroutineResult

interface MarvelRepository {
    fun getDBCharacters(): CoroutineResult<List<Character>>
    fun getCharacter(characterId: Int): CoroutineResult<Character>
    fun insertCharactersToDB(charactersList: List<Character>)
    fun insertCharacterToDB(character: Character)
}
