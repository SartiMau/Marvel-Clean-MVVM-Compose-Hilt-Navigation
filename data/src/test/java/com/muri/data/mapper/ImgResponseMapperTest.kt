package com.muri.data.mapper

import com.muri.data.service.model.ImgResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ImgResponseMapperTest {
    private lateinit var imgResponse: ImgResponse

    @Before
    fun init() {
        imgResponse = ImgResponse(PATH, EXT)
    }

    @Test
    fun `Checking if ImgResponse returns the right value`() {
        val path = imgResponse.path
        val ext = imgResponse.ext
        assertEquals(IMG, "$path$ext")
    }

    companion object {
        private const val PATH = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01"
        private const val EXT = ".jpg"
        private const val IMG = "$PATH$EXT"
    }
}
