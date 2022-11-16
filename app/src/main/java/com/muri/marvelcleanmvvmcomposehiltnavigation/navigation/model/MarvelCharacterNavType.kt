package com.muri.marvelcleanmvvmcomposehiltnavigation.navigation.model

import android.net.Uri
import com.google.gson.Gson
import com.muri.domain.entity.MarvelCharacter

data class NavMarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val img: String
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}

class MarvelCharacterArgType : JsonNavType<NavMarvelCharacter>() {
    override fun fromJsonParse(value: String): NavMarvelCharacter = Gson().fromJson(value, NavMarvelCharacter::class.java)

    override fun NavMarvelCharacter.getJsonParse(): String = Gson().toJson(this)
}

fun MarvelCharacter.toNavMarvelCharacter() = NavMarvelCharacter(id = id, name = name, description = description, img = img)

fun NavMarvelCharacter.toMarvelCharacter() = MarvelCharacter(id = id, name = name, description = description, img = img)
