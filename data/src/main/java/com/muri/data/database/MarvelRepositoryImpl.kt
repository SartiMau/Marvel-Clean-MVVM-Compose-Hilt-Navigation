package com.muri.data.database

import com.muri.data.mapper.mapToCharacterList
import com.muri.data.mapper.mapToDataBaseCharacter
import com.muri.data.mapper.mapToLocalCharacter
import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.utils.CoroutineResult

class MarvelRepositoryImpl(private val charactersDao: MarvelDao) : MarvelRepository {

    override fun getDBCharacters(): CoroutineResult<List<MarvelCharacter>> =
        charactersDao.getDBCharacters().let {
            if (it.isNotEmpty()) {
                CoroutineResult.Success(it.mapToCharacterList())
            } else {
                CoroutineResult.Failure(Exception())
            }
        }

    override fun insertCharactersToDB(charactersList: List<MarvelCharacter>) {
        charactersList.forEach {
            charactersDao.insertCharacter(it.mapToDataBaseCharacter())
        }
    }

    override fun getCharacter(characterId: Int): CoroutineResult<MarvelCharacter> =
        charactersDao.getCharacter(characterId).let {
            if (it.isNotEmpty()) {
                CoroutineResult.Success(it.first().mapToLocalCharacter())
            } else {
                CoroutineResult.Failure(Exception())
            }
        }

    override fun insertCharacterToDB(character: MarvelCharacter) {
        charactersDao.insertCharacter(character.mapToDataBaseCharacter())
    }
}
