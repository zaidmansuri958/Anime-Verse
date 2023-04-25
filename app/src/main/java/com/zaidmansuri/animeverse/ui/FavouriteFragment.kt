package com.zaidmansuri.animeverse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.zaidmansuri.animeverse.R
import com.zaidmansuri.animeverse.adapter.FavouriteAdapter
import com.zaidmansuri.animeverse.databinding.FragmentFavouriteBinding
import com.zaidmansuri.animeverse.db.LikeDao
import com.zaidmansuri.animeverse.db.LikeDataBase
import com.zaidmansuri.animeverse.model.WallpaperModel

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var likeDatabase: LikeDataBase
    private lateinit var likeDao: LikeDao
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)
        likeDatabase = LikeDataBase.getDatabase(requireContext())
        likeDao = likeDatabase.likeDao()
        likeDao.getLikes().observe(viewLifecycleOwner) {
            favouriteAdapter = FavouriteAdapter(it)
            binding.favouriteRecycle.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = favouriteAdapter
            }
        }


    }
}