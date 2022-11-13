package com.muri.domain.service

import com.muri.domain.entity.Character
import com.muri.domain.utils.CoroutineResult

interface CharacterService {
    fun getCharacterList(): CoroutineResult<List<Character>>
    fun getCharacter(characterId: Int): CoroutineResult<Character>
}
