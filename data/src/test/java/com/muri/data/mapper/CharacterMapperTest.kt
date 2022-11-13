package com.muri.data.mapper

import com.muri.domain.entity.MarvelCharacter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterMapperTest {
    private lateinit var character: MarvelCharacter

    @Before
    fun init() {
        character = MarvelCharacter(ID, NAME, DESCRIPTION, IMG)
    }

    @Test
    fun `transforming a Character entity to a CharacterDB entity`() {
        character.mapToDataBaseCharacter()

        assertEquals(ID, character.id)
        assertEquals(NAME, character.name)
        assertEquals(DESCRIPTION, character.description)
        assertEquals(IMG, character.img)
    }

    companion object {
        private const val ID = 1011347
        private const val NAME = "Spider-Man"
        private const val DESCRIPTION = "With great power there must also come great responsibility."
        private const val IMG = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01.jpg"
    }
}
