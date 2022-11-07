package com.mendelin.catpediahilt.presentation.breed_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpediahilt.CatItemBinding

class CatImageAdapter() :
    ListAdapter<String, CatImageAdapter.CatImageViewHolder>(CatImagesCallBack()) {

    private val catImageList: ArrayList<String> = arrayListOf()

    inner class CatImageViewHolder(var binding: CatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.url = url
            binding.executePendingBindings()
        }
    }

    class CatImagesCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatImageViewHolder {
        return CatImageViewHolder(CatItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: CatImageViewHolder, position: Int) {
        val image = catImageList[position]
        holder.bind(image)
    }

    fun setList(list: List<String>) {
        catImageList.apply {
            clear()
            addAll(list)
        }

        submitList(list)
    }

    override fun getItemCount(): Int {
        return catImageList.size
    }
}