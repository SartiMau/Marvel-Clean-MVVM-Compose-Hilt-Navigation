package com.muri.domain.usecase

import com.muri.domain.database.MarvelRepository
import com.muri.domain.entity.MarvelCharacter
import com.muri.domain.service.CharacterService
import com.muri.domain.utils.CoroutineResult
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharacterUseCaseTest {

    @MockK
    private lateinit var characterService: CharacterService
    @MockK
    private lateinit var marvelRepository: MarvelRepository

    private var marvelCharacter: MarvelCharacter = MarvelCharacter(ID, NAME, DESCRIPTION, IMG)

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getCharacterUseCase = GetCharacterUseCaseImpl(characterService, marvelRepository)
    }

    @Test
    fun `if use case returns success`() {
        every { characterService.getCharacter(ID) } returns CoroutineResult.Success(marvelCharacter)
        every { marvelRepository.getCharacter(ID) } returns CoroutineResult.Success(marvelCharacter)

        val result = getCharacterUseCase(marvelCharacter.id)

        verify { marvelRepository.insertCharacterToDB(marvelCharacter) }
        verify { marvelRepository.getCharacter(marvelCharacter.id) }

        assertEquals(marvelCharacter, (result as CoroutineResult.Success).data)
    }

    @Test
    fun `if use case returns failure - !empty DB`() {
        every { characterService.getCharacter(ID) } returns CoroutineResult.Failure(Exception(MSG))
        every { marvelRepository.getCharacter(ID) } returns CoroutineResult.Success(marvelCharacter)

        val result = getCharacterUseCase(ID)

        verify { marvelRepository.getCharacter(ID) }

        assertEquals(marvelCharacter, (result as CoroutineResult.Success).data)
    }

    @Test
    fun `if usecase returns failure - empty DB`() {
        every { characterService.getCharacter(ID) } returns CoroutineResult.Failure(Exception(MSG))
        every { marvelRepository.getCharacter(ID) } returns CoroutineResult.Failure(Exception(MSG))

        val result = getCharacterUseCase(ID)

        verify { marvelRepository.getCharacter(ID) }

        assertEquals(MSG, (result as CoroutineResult.Failure).exception.message)
    }

    companion object {
        private const val ID = 1011347
        private const val NAME = "Spider-Man"
        private const val DESCRIPTION = "With great power there must also come great responsibility."
        private const val IMG = "http://terrigen-cdn-dev.marvel.com/content/prod/1x/203ham_com_crd_01.jpg"
        private const val MSG = "ERROR"
    }
}
