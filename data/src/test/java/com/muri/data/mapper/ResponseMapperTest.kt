package com.muri.data.mapper

import com.muri.data.service.model.CharacterResponse
import com.muri.data.service.model.DataResponse
import com.muri.data.service.model.ImgResponse
import com.muri.data.service.model.ResultResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ResponseMapperTest {
    private lateinit var resultResponse: ResultResponse
    private lateinit var dataResponse: DataResponse
    private lateinit var imgResponse: ImgResponse

    @Before
    fun init() {
        imgResponse = ImgResponse(PATH, EXT)
        resultResponse = ResultResponse(
            mutableListOf(
                CharacterResponse(ID, NAME, DESCRIPTION, imgResponse)
            )
        )
        dataResponse = DataResponse(resultResponse)
    }

    @Test
    fun `transforming data response to character list`() {
        dataResponse.mapToLocalCharacterList()

        assertEquals(ID, resultResponse.characters[0].id)
        assertEquals(NAME, resultResponse.characters[0].name)
        assertEquals(DESCRIPTION, resultResponse.characters[0].description)
        assertEquals(IMG, (resultResponse.characters[0].img.path) + (resultResponse.characters[0].img.ext))
    }

    companion object {
        private const val ID = 1011347
        private const val NAME = "Spider-Man"
        private const val DESCRIPTION = "With great power there must also come great responsibility."
        private const val PATH = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01"
        private const val EXT = ".jpg"
        private const val IMG = "$PATH$EXT"
    }
}
