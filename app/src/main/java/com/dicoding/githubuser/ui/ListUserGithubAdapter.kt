package com.dicoding.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.githubuser.data.response.Users
import com.dicoding.githubuser.databinding.ItemUserBinding


class ListUserGithubAdapter(private val data:MutableList<Users> = mutableListOf(),
                            private val listener: (Users) -> Unit) :
    RecyclerView.Adapter<ListUserGithubAdapter.UserHolder>() {

    fun setData(data: MutableList<Users>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserHolder(private val v:ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: Users) {
            v.userImage.load(item.avatarUrl){
                transformations(CircleCropTransformation())
            }

            v.username.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder =
        UserHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }
}