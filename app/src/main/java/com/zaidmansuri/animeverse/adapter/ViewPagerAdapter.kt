package com.zaidmansuri.animeverse.adapter

import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.zaidmansuri.animeverse.DownloadActivity
import com.zaidmansuri.animeverse.R
import com.zaidmansuri.animeverse.databinding.TrendingCardBinding
import com.zaidmansuri.animeverse.model.WallpaperModel
import java.io.IOException

class ViewPagerAdapter(private val context: Context, val trendingList: ArrayList<WallpaperModel>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return trendingList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.trending_card, null)
        val data = trendingList.get(position)
        val imageView = view.findViewById<ImageView>(R.id.trending_img)
        Glide.with(view.rootView).load(data.image).into(imageView)
        container.addView(view)
        view.setOnClickListener {
            val intent= Intent(context,DownloadActivity::class.java)
            intent.putExtra("image",data.image)
            view.context.startActivity(intent)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
