package com.zaidmansuri.animeverse.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.zaidmansuri.animeverse.DownloadActivity
import com.zaidmansuri.animeverse.databinding.WallpaperCardBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class WallpaperAdapter(val wallpapers: ArrayList<WallpaperModel>) :
    RecyclerView.Adapter<WallpaperViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = WallpaperCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val data = wallpapers.get(position)
        Glide.with(holder.binding.imgWallpaper).load(data.image).into(holder.binding.imgWallpaper)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DownloadActivity::class.java)
            intent.putExtra("name", data.name)
            intent.putExtra("image", data.image)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class WallpaperViewHolder(val binding: WallpaperCardBinding) : ViewHolder(binding.root)