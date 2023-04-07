package com.demircandemir.artlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.demircandemir.artlist.databinding.RecyclerRowBinding
import com.demircandemir.artlist.model.Art
import com.demircandemir.artlist.view.FragmentArtList
import com.demircandemir.artlist.view.FragmentArtListDirections


class ArtAdapter(var artList : List<Art>) : RecyclerView.Adapter<ArtAdapter.ArtHolder>() {

    class ArtHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtHolder(binding)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        holder.binding.artNameText.text  = artList[position].name
        holder.itemView.setOnClickListener {
            val action = FragmentArtListDirections.actionFragmentArtListToFragmentDetail(artList[position].id)
            action.info = "old"
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun setData(newArt : List<Art>){
        artList = newArt
    }
}