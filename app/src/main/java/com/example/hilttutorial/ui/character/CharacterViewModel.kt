package com.example.hilttutorial.ui.character

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hilttutorial.data.entities.Character
import com.example.hilttutorial.data.repository.Repository
import com.example.hilttutorial.utils.Resource

class CharacterViewModel
@ViewModelInject constructor(
    private val repository: Repository
):ViewModel(){
    val characters: LiveData<Resource<List<Character>>> = repository.getCharacters()
}