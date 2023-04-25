package com.zaidmansuri.animeverse

import android.Manifest
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.zaidmansuri.animeverse.databinding.ActivityDownloadBinding
import com.zaidmansuri.animeverse.db.LikeDao
import com.zaidmansuri.animeverse.db.LikeDataBase
import com.zaidmansuri.animeverse.model.WallpaperModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadBinding
    private lateinit var likeDao: LikeDao
    private lateinit var likeDatabase: LikeDataBase
    private lateinit var url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val intent = intent
        url = intent.getStringExtra("image").toString()
        Glide.with(binding.wallpaperImg).load(intent.getStringExtra("image").toString())
            .into(binding.wallpaperImg)

        likeDatabase = LikeDataBase.getDatabase(applicationContext)
        likeDao = likeDatabase.likeDao()

        setLikeIcon()
        binding.like.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                if (!likeDao.likedOrNot(intent.getStringExtra("image").toString())) {
                    likeDao.insertLike(
                        WallpaperModel(null, intent.getStringExtra("image").toString(), "")
                    )
                    setLikeIcon()
                } else {
                    likeDao.removeLike(intent.getStringExtra("image").toString())
                    setLikeIcon()
                }
            }

        }

        binding.download.setOnClickListener {
            chekPermission()
        }

        binding.apply.setOnClickListener {
            setWallpaper()
        }
    }

    fun chekPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        downloadImage()
                    } else {
                        Toast.makeText(
                            this@DownloadActivity,
                            "Please allow all permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                }
            }).check()
    }

    fun downloadImage() {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pd = ProgressDialog(this)
        pd.setTitle("Downloading....")
        pd.setMessage("Please wait Wallpaper is downloading")
        pd.show()
        PRDownloader.download(url, file.path, URLUtil.guessFileName(url, null, null))
            .build()
            .setOnStartOrResumeListener { }
            .setOnProgressListener { }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    pd.dismiss()
                    Toast.makeText(this@DownloadActivity, "Download completed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onError(error: Error) {
                    pd.dismiss()
                    Toast.makeText(this@DownloadActivity, "error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun setWallpaper() {
        val pd = ProgressDialog(this)
        pd.setTitle("Anime Verse")
        pd.setMessage("Painting Wallpaper")
        pd.show()
        val bitmap = (binding.wallpaperImg.getDrawable() as BitmapDrawable).bitmap
        val manager = WallpaperManager.getInstance(applicationContext)
        try {
            manager.setBitmap(bitmap)
            Toast.makeText(this, "Successfully Applied", Toast.LENGTH_SHORT).show()
            pd.dismiss()
        } catch (e: IOException) {
            e.printStackTrace()
            pd.dismiss()
            Toast.makeText(this, "error" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun setLikeIcon() {
        GlobalScope.launch {
            if (likeDao.likedOrNot(intent.getStringExtra("image").toString())) {
                binding.like.setImageResource(R.drawable.liked)
            } else {
                binding.like.setImageResource(R.drawable.ic_heart)
            }
        }
    }
}