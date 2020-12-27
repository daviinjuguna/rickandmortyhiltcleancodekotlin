package com.example.hilttutorial.ui.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.hilttutorial.R
import com.example.hilttutorial.data.entities.Character

class CharacterAdapter (private val listener:CharacterItemListener): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private val items = ArrayList<Character>()

    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent,false),listener
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount():Int = items.size

    class CharacterViewHolder(itemView: View,private val listener: CharacterItemListener):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private val characterName: TextView = itemView.findViewById(R.id.name)
        private val characterSpecies:TextView = itemView.findViewById(R.id.species_and_status)
        private val characterImage:AppCompatImageView = itemView.findViewById(R.id.image)

        private lateinit var character: Character

        override fun onClick(v: View?) {
            listener.onClickedCharacter(character.id)
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Character){
            this.character = item

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(item.image)
                .transform(CircleCrop())
                .into(characterImage)

            characterName.text = item.name
            characterSpecies.text = item.species
        }

    }
}