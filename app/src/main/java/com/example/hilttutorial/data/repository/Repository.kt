package com.example.hilttutorial.data.repository

import com.example.hilttutorial.data.local.CharacterDao
import com.example.hilttutorial.data.remote.CharacterRemoteDataSource
import com.example.hilttutorial.utils.performGetOperation
import javax.inject.Inject

class Repository
@Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getSingleCharacter(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = { localDataSource.getAllCharacters() },
        networkCall = { remoteDataSource.getCharacters() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}