package com.muri.data.database

import com.muri.data.mapper.mapToCharacterList
import com.muri.data.mapper.mapToDataBaseCharacter
import com.muri.data.mapper.mapToLocalCharacter
import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.Character
import com.muri.domain.utils.Result

class MarvelRepositoryImpl(private val charactersDao: MarvelDao) : MarvelRepository {

    override fun getDBCharacters(): Result<List<Character>> =
        charactersDao.getDBCharacters().let {
            if (it.isNotEmpty()) {
                Result.Success(it.mapToCharacterList())
            } else {
                Result.Failure(Exception())
            }
        }

    override fun insertCharactersToDB(charactersList: List<Character>) {
        charactersList.forEach {
            charactersDao.insertCharacter(it.mapToDataBaseCharacter())
        }
    }

    override fun getCharacter(id: String): Result<Character> =
        charactersDao.getCharacter(id).let {
            if (it.isNotEmpty()) {
                Result.Success(it.first().mapToLocalCharacter())
            } else {
                Result.Failure(Exception())
            }
        }

    override fun insertCharacterToDB(character: Character) {
        charactersDao.insertCharacter(character.mapToDataBaseCharacter())
    }
}
