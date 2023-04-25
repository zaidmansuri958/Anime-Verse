package com.zaidmansuri.animeverse.ui

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zaidmansuri.animeverse.R
import com.zaidmansuri.animeverse.adapter.CategoryAdapter
import com.zaidmansuri.animeverse.adapter.WallpaperAdapter
import com.zaidmansuri.animeverse.databinding.FragmentHomeBinding
import com.zaidmansuri.animeverse.model.WallpaperModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var DatabaseReference: DatabaseReference
    private lateinit var wallpaperAdapter: WallpaperAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setWallpapers()
        setCategory()

        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(
            "sharedPrefs", MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences
            .getBoolean(
                "isDarkModeOn", false
            )

        if (isDarkModeOn) {
            binding.theme.setImageResource(R.drawable.light)
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES
                );
        } else {
            binding.theme.setImageResource(R.drawable.dark)
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO
                );
        }

        binding.theme.setOnClickListener {
            if (isDarkModeOn) {
                binding.theme.setImageResource(R.drawable.light)
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    );
                editor.putBoolean(
                    "isDarkModeOn", false
                );
                editor.apply();

            } else {
                binding.theme.setImageResource(R.drawable.dark)
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES
                    );
                editor.putBoolean(
                    "isDarkModeOn", true
                );
                editor.apply();

            }
        }
    }

    private fun setWallpapers() {
        DatabaseReference = FirebaseDatabase.getInstance().reference.child("HomeWallpaper")
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
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.nestedView.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.wallpaperRecycle.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun setCategory() {
        DatabaseReference = FirebaseDatabase.getInstance().reference.child("Category")
        DatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = arrayListOf<WallpaperModel>()
                for (i in snapshot.children) {
                    val data = i.getValue(WallpaperModel::class.java)
                    categories.add(data!!)
                }
                categories.shuffle()
                if (snapshot.exists()) {
                    categoryAdapter = CategoryAdapter(categories)
                    binding.categoryRecycle.adapter = categoryAdapter
                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.categoryRecycle.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
}