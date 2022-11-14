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

class GetCharacterListUseCaseTest {

    @MockK
    private lateinit var characterService: CharacterService
    @MockK
    private lateinit var marvelRepository: MarvelRepository
    @MockK
    private lateinit var characterList: List<MarvelCharacter>

    private lateinit var getCharacterListUseCase: GetCharacterListUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getCharacterListUseCase = GetCharacterListUseCaseImpl(characterService, marvelRepository)
    }

    @Test
    fun `if use case returns success`() {
        every { characterService.getCharacterList() } returns CoroutineResult.Success(characterList)
        every { marvelRepository.getDBCharacters() } returns CoroutineResult.Success(characterList)

        val result = getCharacterListUseCase()

        verify { marvelRepository.insertCharactersToDB(characterList) }
        verify { marvelRepository.getDBCharacters() }

        assertEquals(characterList, (result as CoroutineResult.Success).data)
    }

    @Test
    fun `if use case returns failure - empty DB`() {
        every { characterService.getCharacterList() } returns CoroutineResult.Failure(Exception(MSG))
        every { marvelRepository.getDBCharacters() } returns CoroutineResult.Failure(Exception(MSG))

        val result = getCharacterListUseCase()

        verify { marvelRepository.getDBCharacters() }

        assertEquals(MSG, (result as CoroutineResult.Failure).exception.message)
    }

    @Test
    fun `if use case returns failure - !empty DB`() {
        every { characterService.getCharacterList() } returns CoroutineResult.Failure(Exception(MSG))
        every { marvelRepository.getDBCharacters() } returns CoroutineResult.Success(characterList)

        val result = getCharacterListUseCase()

        verify { marvelRepository.getDBCharacters() }

        assertEquals(characterList, (result as CoroutineResult.Success).data)
    }

    companion object {
        private const val MSG = "ERROR"
    }
}
