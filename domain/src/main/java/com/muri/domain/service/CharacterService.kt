package com.muri.domain.service

import com.muri.domain.entity.Character
import com.muri.domain.utils.Result

interface CharacterService {
    fun getCharacterList(): Result<List<Character>>
    fun getCharacter(id: String): Result<Character>
}
