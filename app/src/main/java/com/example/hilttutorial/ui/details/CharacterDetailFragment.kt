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
import kotlinx.android.synthetic.main.fragment_character_detail.*

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                    progress_bar.visibility = View.GONE
                    character_cl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    character_cl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacters(characters:Character){
        name.text = characters.name
        species.text = characters.species
        status.text = characters.status
        gender.text = characters.gender
//        location.text = characters.location.name
        origin.text = characters.origin.name

//        context?.let {
//            Glide.with(it)
//                .load(characters.image)
//                .transform(CircleCrop())
//                .into(image)
//        }
        activity?.let {
            Glide.with(it.applicationContext)
                .load(characters.image)
                .transform(CircleCrop())
                .into(image)
        }
    }
}