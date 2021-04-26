package com.alexlianardo.github2

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.alexlianardo.github2.adapter.SectionsPagerAdapter
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.NAME
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.PHOTO
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.REPOSITORIES
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.alexlianardo.github2.database.FavoriteHelper
import com.alexlianardo.github2.model.Users
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_users_detail.*

class UsersDetailActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        const val EXTRA_USERS = "extra_users"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private var isFavorite = false
    private lateinit var gitHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

        val favorites = intent.getParcelableExtra(EXTRA_USERS) as Users?

        val cursor: Cursor = gitHelper.queryById(favorites?.username.toString())

        if (cursor.moveToNext()) {
            isFavorite = true
            setFavorite(true)
        }
        else
        {
            isFavorite = false
            setFavorite(false)
        }

        btn_fav.setOnClickListener(this)

        getData()
        viewPagerConfig()

    }

    private fun viewPagerConfig(){
        //pager
        val sectionAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setFavorite(status: Boolean) {
        if(status){
            btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else if (!status){
            btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun getData() {
        val users = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
        detail_name.text = users.name
        detail_username.text = users.username
        detail_company.text = users.company
        detail_location.text = users.location
        detail_repository.text = users.repository
        detail_followers.text = users.followers
        detail_following.text = users.following
        Glide.with(this)
            .load(users.photo)
            .into(detail_img_user)
    }

    override fun onClick(view: View) {
        val users = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
        when (view.id) {
            R.id.btn_fav -> {
                if (isFavorite) {
                    val id = users.username.toString()
                    gitHelper.deleteById(id)
                    Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT)
                        .show()
                    setFavorite(false)
                    isFavorite = false
                } else {
                    val values = ContentValues()
                    values.put(USERNAME, users.username)
                    values.put(NAME, users.name)
                    values.put(PHOTO, users.photo)
                    values.put(COMPANY, users.company)
                    values.put(LOCATION, users.location)
                    values.put(REPOSITORIES, users.repository)
                    values.put(FOLLOWERS, users.followers)
                    values.put(FOLLOWING, users.following)
                    values.put(FAVORITE, "favorite")

                    contentResolver.insert(CONTENT_URI, values)
                    Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT)
                        .show()
                    isFavorite = true
                    setFavorite(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }
}