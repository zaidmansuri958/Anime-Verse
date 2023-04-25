package com.zaidmansuri.animeverse.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.animeverse.WallpaperActivity
import com.zaidmansuri.animeverse.databinding.CategoryCardBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class CategoryAdapter(val categoryList: ArrayList<WallpaperModel>) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = categoryList.get(position)
        Glide.with(holder.binding.categoryImg).load(data.image).into(holder.binding.categoryImg)
        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,WallpaperActivity::class.java)
            intent.putExtra("name",data.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class CategoryViewHolder(val binding: CategoryCardBinding) : ViewHolder(binding.root)