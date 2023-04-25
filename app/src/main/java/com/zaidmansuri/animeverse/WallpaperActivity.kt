package com.zaidmansuri.animeverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zaidmansuri.animeverse.adapter.WallpaperAdapter
import com.zaidmansuri.animeverse.databinding.ActivityWallpaperBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class WallpaperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var DatabaseReference: DatabaseReference
    private lateinit var wallpaperAdapter: WallpaperAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        val intent = intent
        binding.categoryTxt.text = intent.getStringExtra("name")
        setWallpapers()
    }

    private fun setWallpapers() {
        DatabaseReference =
            FirebaseDatabase.getInstance().reference.child(binding.categoryTxt.text.toString())
        DatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val wallpapers = arrayListOf<WallpaperModel>()
                for (i in snapshot.children) {
                    val data = i.getValue(WallpaperModel::class.java)
                    wallpapers.add(data!!)
                }
                wallpapers.shuffle()
                if (snapshot.exists()) {
                    wallpaperAdapter = WallpaperAdapter(wallpapers)
                    binding.wallpaperRecycle.adapter = wallpaperAdapter
                } else {
                    Toast.makeText(
                        this@WallpaperActivity,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                binding.shimmerWallpaper.stopShimmer()
                binding.shimmerWallpaper.visibility = View.GONE
                binding.wallpaper.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@WallpaperActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.wallpaperRecycle.layoutManager = GridLayoutManager(this, 3)
    }

}