package com.alexlianardo.consumerapp

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.alexlianardo.consumerapp.adapter.SectionsPagerAdapter
import com.alexlianardo.consumerapp.database.FavoriteHelper
import com.alexlianardo.consumerapp.model.Users
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_users_detail.*

class UsersDetailActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_USERS = "extra_users"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var gitHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

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

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }
}