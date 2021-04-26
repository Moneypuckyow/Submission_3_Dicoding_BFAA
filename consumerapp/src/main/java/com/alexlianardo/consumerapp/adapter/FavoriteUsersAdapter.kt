package com.alexlianardo.consumerapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexlianardo.consumerapp.R
import com.alexlianardo.consumerapp.UsersDetailActivity
import com.alexlianardo.consumerapp.UsersFavoriteActivity
import com.alexlianardo.consumerapp.model.FavoriteData
import com.alexlianardo.consumerapp.model.Users
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.users_item.view.*
import java.util.ArrayList

class FavoriteUsersAdapter(activity: UsersFavoriteActivity) :  RecyclerView.Adapter<FavoriteUsersAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<FavoriteData>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])

        val data = listFavorite[position]
        holder.itemView.setOnClickListener {
            val dataUser = Users(
                data.username,
                data.name,
                data.company,
                data.location,
                data.photo,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, UsersDetailActivity::class.java)
            mIntent.putExtra(UsersDetailActivity.EXTRA_USERS, dataUser)
            it.context.startActivity(mIntent)
        }
    }

        override fun getItemCount(): Int = this.listFavorite.size

        inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(fav: FavoriteData) {
                with(itemView) {
                    Glide.with(itemView.context)
                        .load(fav.photo)
                        .apply(RequestOptions().override(250, 250))
                        .into(itemView.img_user)
                    txt_username.text = fav.username
                    txt_name.text = fav.name
                    txt_followers.text = fav.followers
                    txt_following.text = fav.following
                }
            }
        }
}