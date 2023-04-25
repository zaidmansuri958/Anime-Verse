package com.zaidmansuri.animeverse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zaidmansuri.animeverse.R
import com.zaidmansuri.animeverse.adapter.CategoryAdapter
import com.zaidmansuri.animeverse.databinding.FragmentCategoryBinding
import com.zaidmansuri.animeverse.model.WallpaperModel

class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var DatabaseReference:DatabaseReference
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCategoryBinding.bind(view)
        setCategory()
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
                binding.shimmerCategory.stopShimmer()
                binding.shimmerCategory.visibility=View.GONE
                binding.categoryLayout.visibility=View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.categoryRecycle.layoutManager =
            LinearLayoutManager(activity)
    }

}