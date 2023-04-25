package com.zaidmansuri.animeverse.ui

import android.os.Bundle
import android.util.LayoutDirection
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager.PageTransformer
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zaidmansuri.animeverse.R
import com.zaidmansuri.animeverse.adapter.ViewPagerAdapter
import com.zaidmansuri.animeverse.adapter.WallpaperAdapter
import com.zaidmansuri.animeverse.databinding.FragmentTrendingBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private lateinit var DatabaseReference: DatabaseReference
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrendingBinding.bind(view)
        binding.viewpager.offscreenPageLimit = 3
        setWallpapers()
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
                    viewPagerAdapter = ViewPagerAdapter(requireContext(), wallpapers)
                    binding.viewpager.setPadding(100, 0, 100, 0)
                    binding.viewpager.adapter = viewPagerAdapter
                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.trendingShimmer.stopShimmer()
                binding.trendingShimmer.visibility = View.GONE
                binding.trendingCard.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }
}