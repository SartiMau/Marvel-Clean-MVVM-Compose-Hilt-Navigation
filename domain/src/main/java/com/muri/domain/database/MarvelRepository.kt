package com.muri.domain.database

import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.utils.CoroutineResult

interface MarvelRepository {
    fun getDBCharacters(): CoroutineResult<List<MarvelCharacter>>
    fun getCharacter(characterId: Int): CoroutineResult<MarvelCharacter>
    fun insertCharactersToDB(charactersList: List<MarvelCharacter>)
    fun insertCharacterToDB(character: MarvelCharacter)
}
