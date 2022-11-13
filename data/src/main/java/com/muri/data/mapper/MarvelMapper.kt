package com.muri.data.mapper

import com.muri.data.database.entity.CharacterEntity
import com.muri.data.service.model.CharacterResponse
import com.muri.data.service.model.DataResponse
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.utils.Constants.DOT

fun DataResponse.mapToLocalCharacterList(): List<MarvelCharacter> = data.characters.map { it.mapToLocalCharacter() }

fun CharacterResponse.mapToLocalCharacter() =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        img = "${img.path}$DOT${img.ext}"
    )

fun CharacterEntity.mapToLocalCharacter() =
    MarvelCharacter(
        id = id,
        name = name,
        description = description,
        img = img
    )

fun MarvelCharacter.mapToDataBaseCharacter() =
    CharacterEntity(
        id = id,
        name = name,
        description = description,
        img = img
    )

fun List<CharacterEntity>.mapToCharacterList() = this.map { it.mapToLocalCharacter() }
