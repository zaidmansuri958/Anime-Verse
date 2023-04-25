package com.zaidmansuri.animeverse

import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.database.*
import com.zaidmansuri.animeverse.databinding.ActivityMainBinding
import com.zaidmansuri.animeverse.db.LikeDataBase
import com.zaidmansuri.animeverse.ui.CategoryFragment
import com.zaidmansuri.animeverse.ui.FavouriteFragment
import com.zaidmansuri.animeverse.ui.HomeFragment
import com.zaidmansuri.animeverse.ui.TrendingFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: LikeDataBase
    private lateinit var bnv: MeowBottomNavigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        database =
            Room.databaseBuilder(applicationContext, LikeDataBase::class.java, "like_db").build()
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
        bnv = findViewById<MeowBottomNavigation>(R.id.bottomNav)
        bnv.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
        bnv.add(MeowBottomNavigation.Model(2, R.drawable.ic_hot))
        bnv.add(MeowBottomNavigation.Model(3, R.drawable.ic_category))
        bnv.add(MeowBottomNavigation.Model(4, R.drawable.like))

        binding.bottomNav.setOnClickMenuListener {
        }

        binding.bottomNav.setOnShowListener() {
            val id = it.id
            var fragment: Fragment? = null
            when (id) {
                1 -> {
                    fragment = HomeFragment()
                }
                2 -> {
                    fragment = TrendingFragment()
                }
                3 -> {
                    fragment = CategoryFragment()
                }
                4 -> {
                    fragment = FavouriteFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment!!)
            transaction.commit()

        }
        binding.bottomNav.show(1, true)



    }
}