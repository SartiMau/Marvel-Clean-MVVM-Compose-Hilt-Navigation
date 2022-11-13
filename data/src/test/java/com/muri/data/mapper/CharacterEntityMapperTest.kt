package com.muri.data.mapper

import com.muri.data.database.entity.CharacterEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterEntityMapperTest {
    private lateinit var characterEntity: CharacterEntity
    private lateinit var characterEntityList: List<CharacterEntity>

    @Before
    fun init() {
        characterEntity = CharacterEntity(ID, NAME, DESCRIPTION, IMG)
        characterEntityList = mutableListOf(CharacterEntity(ID, NAME, DESCRIPTION, IMG))
    }

    @Test
    fun `transforming a Character entity list to a CharacterDB entity list`() {
        val dbListToCharacterList = characterEntityList.mapToCharacterList()

        assertEquals(ID, dbListToCharacterList[0].id)
        assertEquals(NAME, dbListToCharacterList[0].name)
        assertEquals(DESCRIPTION, dbListToCharacterList[0].description)
        assertEquals(IMG, dbListToCharacterList[0].img)
    }

    @Test
    fun `transforming a CharacterDB entity to a Character entity`() {
        val dbToCharacter = characterEntity.mapToLocalCharacter()

        assertEquals(ID, dbToCharacter.id)
        assertEquals(NAME, dbToCharacter.name)
        assertEquals(DESCRIPTION, dbToCharacter.description)
        assertEquals(IMG, dbToCharacter.img)
    }

    companion object {
        private const val ID = 1011347
        private const val NAME = "Spider-Man"
        private const val DESCRIPTION = "With great power there must also come great responsibility."
        private const val IMG = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01.jpg"
    }
}
