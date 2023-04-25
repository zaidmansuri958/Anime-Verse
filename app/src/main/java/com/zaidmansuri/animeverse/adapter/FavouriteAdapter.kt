package com.zaidmansuri.animeverse.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.animeverse.DownloadActivity
import com.zaidmansuri.animeverse.databinding.FavouriteCardBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class FavouriteAdapter(private val favouriteItems: List<WallpaperModel>) :
    RecyclerView.Adapter<FavouriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = FavouriteCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favouriteItems.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val data = favouriteItems.get(position)
        Glide.with(holder.itemView).load(data.image).into(holder.binding.favouriteImg)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DownloadActivity::class.java)
            intent.putExtra("image", data.image)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class FavouriteViewHolder(val binding: FavouriteCardBinding) : ViewHolder(binding.root)