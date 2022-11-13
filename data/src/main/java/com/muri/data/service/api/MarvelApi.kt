package com.muri.data.service.api

import com.muri.data.service.model.CharacterResponse
import com.muri.data.service.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {

    @GET("/v1/public/characters")
    fun getCharacterList(): Call<DataResponse>

    @GET("/v1/public/characters/characterId")
    fun getCharacter(@Path("characterId") characterId: Int): Call<CharacterResponse>
}
