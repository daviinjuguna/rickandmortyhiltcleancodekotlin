package com.example.hilttutorial.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.hilttutorial.data.entities.Character
import com.example.hilttutorial.data.repository.Repository
import com.example.hilttutorial.utils.Resource

class CharacterDetailViewModel
@ViewModelInject constructor(
    private val repository: Repository
):ViewModel(){
   private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { 
        i ->repository.getCharacter(i)
    }

    val character : LiveData<Resource<Character>> = _character

    fun start(id:Int){
        _id.value = id
    }
}