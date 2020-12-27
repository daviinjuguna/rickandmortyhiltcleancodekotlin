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

@AndroidEntryPoint
class CharacterFragment : Fragment() ,CharacterAdapter.CharacterItemListener{

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter
    private lateinit var charactersRv:RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        charactersRv.findViewById<RecyclerView>(R.id.characters_rv)
        progressBar.findViewById<ProgressBar>(R.id.progress_bar)
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
                    progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter(this)
        charactersRv.layoutManager = LinearLayoutManager(requireContext())
        charactersRv.adapter = adapter
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf("id" to characterId)
        )
    }
}