package com.muri.data.service

import com.muri.data.mapper.mapToLocalCharacter
import com.muri.data.mapper.mapToLocalCharacterList
import com.muri.data.service.api.MarvelApi
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.CoroutineResult
import javax.inject.Inject

class CharacterServiceImpl @Inject constructor(private val marvelApi: MarvelApi) : CharacterService {

    override fun getCharacterList(): CoroutineResult<List<MarvelCharacter>> {
        try {
            val callResponse = marvelApi.getCharacterList()
            val response = callResponse.execute()
            if (response.isSuccessful) response.body()?.let {
                return CoroutineResult.Success(it.mapToLocalCharacterList())
            }
        } catch (e: Exception) {
            return CoroutineResult.Failure(Exception())
        }
        return CoroutineResult.Failure(Exception())
    }

    override fun getCharacter(characterId: Int): CoroutineResult<MarvelCharacter> {
        try {
            val callResponse = marvelApi.getCharacter(characterId)
            val response = callResponse.execute()
            if (response.isSuccessful) response.body()?.let {
                return CoroutineResult.Success(it.mapToLocalCharacter())
            }
        } catch (e: Exception) {
            return CoroutineResult.Failure(Exception())
        }
        return CoroutineResult.Failure(Exception())
    }
}
