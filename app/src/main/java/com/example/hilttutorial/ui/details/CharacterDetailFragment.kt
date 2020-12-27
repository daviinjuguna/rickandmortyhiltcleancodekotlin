package com.example.hilttutorial.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.hilttutorial.R
import com.example.hilttutorial.data.entities.Character
import com.example.hilttutorial.data.entities.Origin
import com.example.hilttutorial.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var image:AppCompatImageView
    private lateinit var name:TextView
    private lateinit var species:TextView
    private lateinit var status:TextView
    private lateinit var gender:TextView
    private lateinit var location:TextView
    private lateinit var origin: TextView
    private lateinit var characterCl:ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        progressBar.findViewById<ProgressBar>(R.id.progress_bar)
        image.findViewById<AppCompatImageView>(R.id.image)
        name.findViewById<TextView>(R.id.name)
        species.findViewById<TextView>(R.id.species)
        status.findViewById<TextView>(R.id.status)
        gender.findViewById<TextView>(R.id.gender)
        location.findViewById<TextView>(R.id.location)
        origin.findViewById<TextView>(R.id.origin)
        characterCl.findViewById<ConstraintLayout>(R.id.character_cl)
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id").let {
            if (it != null) {
                viewModel.start(it)
            }
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    bindCharacters(it.data!!)
                    progressBar.visibility = View.GONE
                    characterCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    characterCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacters(characters:Character){
        name.text = characters.name
        species.text = characters.species
        status.text = characters.status
        gender.text = characters.gender
        location.text = characters.location.name
        origin.text = characters.origin.name

        context?.let {
            Glide.with(it)
                .load(characters.image)
                .transform(CircleCrop())
                .into(image)
        }
    }
}