package com.alexlianardo.github2

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexlianardo.github2.adapter.FavoriteUsersAdapter
import com.alexlianardo.github2.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.alexlianardo.github2.database.FavoriteHelper
import com.alexlianardo.github2.helper.MappingHelper
import com.alexlianardo.github2.model.FavoriteData
import kotlinx.android.synthetic.main.activity_users_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersFavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUsersAdapter
    private lateinit var gitHelper: FavoriteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_favorite)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

        recycleViewFavorite.layoutManager = LinearLayoutManager(this)
        recycleViewFavorite.setHasFixedSize(true)
        adapter = FavoriteUsersAdapter(this)
        recycleViewFavorite.adapter = adapter

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        loadFavoriteAsync()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavoriteAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavoriteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    private fun showSnackMessage() {
        Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
    }


    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFavorite.visibility = View.VISIBLE
            val differedFav = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = differedFav.await()
            progressBarFavorite.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listFavorite = favData
            } else {
                adapter.listFavorite = ArrayList()
                showSnackMessage()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
    }

}