package com.muri.data.service

import com.muri.data.mapper.mapToLocalCharacter
import com.muri.data.mapper.mapToLocalCharacterList
import com.muri.data.service.api.MarvelApi
import com.muri.domain.entity.Character
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.Result
import javax.inject.Inject

class CharacterServiceImpl @Inject constructor(private val marvelApi: MarvelApi) : CharacterService {

    override fun getCharacterList(): Result<List<Character>> {
        try {
            val callResponse = marvelApi.getCharacterList()
            val response = callResponse.execute()
            if (response.isSuccessful) response.body()?.let {
                return Result.Success(it.mapToLocalCharacterList())
            }
        } catch (e: Exception) {
            return Result.Failure(Exception())
        }
        return Result.Failure(Exception())
    }

    override fun getCharacter(id: String): Result<Character> {
        try {
            val callResponse = marvelApi.getCharacter(id)
            val response = callResponse.execute()
            if (response.isSuccessful) response.body()?.let {
                return Result.Success(it.mapToLocalCharacter())
            }
        } catch (e: Exception) {
            return Result.Failure(Exception())
        }
        return Result.Failure(Exception())
    }
}
