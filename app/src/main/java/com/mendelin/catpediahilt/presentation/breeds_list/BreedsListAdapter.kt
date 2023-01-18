package com.mendelin.catpediahilt.presentation.breeds_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpediahilt.BreedItemBinding
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.presentation.main.BreedCallback

class BreedsListAdapter(val callback: BreedCallback) :
    ListAdapter<Breed, BreedsListAdapter.BreedsListViewHolder>(BreedsListDiffCallBack()) {

    private val breedsList: ArrayList<Breed> = arrayListOf()

    inner class BreedsListViewHolder(var binding: BreedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.breed = breed
            binding.listener = callback
            binding.executePendingBindings()
        }
    }

    class BreedsListDiffCallBack : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsListViewHolder {
        return BreedsListViewHolder(BreedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: BreedsListViewHolder, position: Int) {
        val breed = breedsList[position]
        holder.bind(breed)
    }

    fun setList(list: List<Breed>) {
        breedsList.apply {
            clear()
            addAll(list)
        }

        submitList(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount(): Int {
        return breedsList.size
    }
}