package com.alexlianardo.consumerapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexlianardo.consumerapp.adapter.ListUsersAdapter
import com.alexlianardo.consumerapp.databinding.ActivityMainBinding
import com.alexlianardo.consumerapp.model.Users
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONObject
import java.BuildConfig
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val key = BuildConfig.API_KEY
    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<Users> = ArrayList()
    private lateinit var adapter: ListUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUsersAdapter(list)
        showRecyclerList()
        getMoneypuckyowUsers()
        searchUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> {
                val mIntent = Intent(this, UsersFavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_notif -> {
                val mIntent = Intent(this, NotificationActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_languange -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser() {
        search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(username: String): Boolean {
            if (username.isEmpty()) {
                return true
            } else {
                list.clear()
                searchUsersData(username)
            }
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    })
    }

    //memperlihatkan list "username" user yang di cari
    private fun showRecyclerList(){
        recycleView.layoutManager = LinearLayoutManager(this)
        val listUsersAdapter = ListUsersAdapter(list)
        recycleView.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                //mengirim data yang didapatkan dari function searchUsersList
                showDetailPage(data)
            }
        })
    }

    //mengambil data "username" dari user untuk di kirimkan ke UsersDetailActivity dan disimpan di EXTRA_USERS
    private fun showDetailPage(users: Users) {
        val moveWithDataIntent = Intent (this, UsersDetailActivity::class.java)
        moveWithDataIntent.putExtra(UsersDetailActivity.EXTRA_USERS, users)
        this.startActivity(moveWithDataIntent)
    }

    //function untuk mencari "username" user
    private fun searchUsersData(id: String) {
            //memperlihatkan progressBar
            binding.progressBar.visibility = View.VISIBLE
            val client = AsyncHttpClient()
            val url = "https://api.github.com/search/users?q=$id"
            client.addHeader("Authorization", "token $key")
            client.addHeader("User-Agent", "request")
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    //progressBar hilang pada saat pengambilan data API berhasil
                    binding.progressBar.visibility = View.INVISIBLE
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val responseObject = JSONObject(result)
                        val jsonArray = responseObject.getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val username = jsonObject.getString("login")
                            getDetailUsers(username)
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                    binding.progressBar.visibility = View.INVISIBLE

                    val errorMessage = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getMoneypuckyowUsers() {
        if(progressBar != null) {
            progressBar.visibility = View.GONE
        }
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $key")
        val url = "https://api.github.com/users/moneypuckyow"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString().toLowerCase(Locale.ROOT)
                    val name: String = jsonObject.getString("name")
                    val photo: String = jsonObject.getString("avatar_url")
                    val company: String = jsonObject.getString("company")
                    val location: String = jsonObject.getString("location")
                    val repository: String = jsonObject.getString("public_repos")
                    val followers: String = jsonObject.getString("followers")
                    val following: String = jsonObject.getString("following")
                    list.add(Users(username, name, company, location, photo, repository, followers, following))
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBar.visibility = View.VISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun getDetailUsers(id: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString().toLowerCase(Locale.ROOT)
                    val name: String = jsonObject.getString("name")
                    val photo: String = jsonObject.getString("avatar_url")
                    val company: String = jsonObject.getString("company")
                    val location: String = jsonObject.getString("location")
                    val repository: String = jsonObject.getString("public_repos")
                    val followers: String = jsonObject.getString("followers")
                    val following: String = jsonObject.getString("following")
                    list.add(Users(username, name, company, location, photo, repository, followers, following))
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                progressBar.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
}