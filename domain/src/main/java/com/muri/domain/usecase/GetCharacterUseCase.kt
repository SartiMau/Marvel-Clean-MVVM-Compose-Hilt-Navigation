package com.muri.domain.usecase

import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.Character
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.Result

interface GetCharacterUseCase {
    operator fun invoke(characterId: String): Result<Character>
}

class GetCharacterUseCaseImpl(
    private val characterService: CharacterService,
    private val db: MarvelRepository
) : GetCharacterUseCase {
    override operator fun invoke(id: String): Result<Character> {
        return when (val serviceResult = characterService.getCharacter(id)) {
            is Result.Success -> {
                db.insertCharacterToDB(serviceResult.data)
                db.getCharacter(serviceResult.data.id)
            }
            is Result.Failure -> db.getCharacter(id)
        }
    }
}
