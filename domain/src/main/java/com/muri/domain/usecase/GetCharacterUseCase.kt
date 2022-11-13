package com.muri.domain.usecase

import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.Character
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.CoroutineResult
import javax.inject.Inject

interface GetCharacterUseCase {
    operator fun invoke(characterId: Int): CoroutineResult<Character>
}

class GetCharacterUseCaseImpl @Inject constructor(
    private val characterService: CharacterService,
    private val db: MarvelRepository
) : GetCharacterUseCase {
    override operator fun invoke(characterId: Int): CoroutineResult<Character> {
        return when (val serviceResult = characterService.getCharacter(characterId)) {
            is CoroutineResult.Success -> {
                db.insertCharacterToDB(serviceResult.data)
                db.getCharacter(serviceResult.data.id)
            }
            is CoroutineResult.Failure -> db.getCharacter(characterId)
        }
    }
}
