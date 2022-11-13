package com.muri.domain.service

import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.utils.CoroutineResult

interface CharacterService {
    fun getCharacterList(): CoroutineResult<List<MarvelCharacter>>
    fun getCharacter(characterId: Int): CoroutineResult<MarvelCharacter>
}
