package com.muri.domain.usecase

import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.Character
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.Result

interface GetCharactersUseCase {
    operator fun invoke(): Result<List<Character>>
}

class GetCharactersUseCaseImpl(
    private val characterService: CharacterService,
    private val marvelRepository: MarvelRepository
) : GetCharactersUseCase {
    override operator fun invoke(): Result<List<Character>> {
        return when (val serviceResult = characterService.getCharacterList()) {
            is Result.Success -> {
                marvelRepository.insertCharactersToDB(serviceResult.data)
                marvelRepository.getDBCharacters()
            }
            is Result.Failure -> marvelRepository.getDBCharacters()
        }
    }
}
