package com.muri.domain.usecase

import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.CoroutineResult
import javax.inject.Inject

interface GetCharacterListUseCase {
    operator fun invoke(): CoroutineResult<List<MarvelCharacter>>
}

class GetCharacterListUseCaseImpl @Inject constructor(
    private val characterService: CharacterService,
    private val marvelRepository: MarvelRepository
) : GetCharacterListUseCase {
    override operator fun invoke(): CoroutineResult<List<MarvelCharacter>> {
        return when (val serviceResult = characterService.getCharacterList()) {
            is CoroutineResult.Success -> {
                marvelRepository.insertCharactersToDB(serviceResult.data)
                marvelRepository.getDBCharacters()
            }
            is CoroutineResult.Failure -> marvelRepository.getDBCharacters()
        }
    }
}
