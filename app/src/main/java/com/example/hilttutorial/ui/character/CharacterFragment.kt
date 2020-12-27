package com.example.hilttutorial.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character.*

@AndroidEntryPoint
class CharacterFragment : Fragment() ,CharacterAdapter.CharacterItemListener{

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    progress_bar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) characterAdapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    progress_bar.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView() {
//        adapter = CharacterAdapter(this)
//        charactersRv.layoutManager = LinearLayoutManager(requireContext())
//        charactersRv.adapter = adapter
        characters_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            characterAdapter = CharacterAdapter(this@CharacterFragment)
            adapter = characterAdapter

        }
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf("id" to characterId)
        )
    }
}