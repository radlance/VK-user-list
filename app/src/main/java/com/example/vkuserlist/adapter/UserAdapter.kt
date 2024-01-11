package com.example.vkuserlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vkuserlist.R
import com.example.vkuserlist.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var users: MutableList<User> = ArrayList()

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: UserItemBinding = UserItemBinding.bind(itemView)

        fun bind(user: User) = with(binding) {
            userNameTv.text = user.first_name
            userLastNameTv.text = user.last_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }
    

    fun addUser(user: User) {
        users.add(user)
        notifyDataSetChanged()
    }

    fun clearUsers() {
        users.clear()
    }

    fun removeItem(position: Int) {
        users.removeAt(position)
        notifyItemRemoved(position)
    }
}