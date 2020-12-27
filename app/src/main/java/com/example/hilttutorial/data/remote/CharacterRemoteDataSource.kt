package com.example.hilttutorial.data.remote

import com.example.hilttutorial.data.api.CharacterService
import javax.inject.Inject

class CharacterRemoteDataSource
@Inject constructor(
    private val characterService:CharacterService
):BaseDataSource(){
    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getSingleCharacter(id:Int) = getResult { characterService.getCharacter(id) }
}