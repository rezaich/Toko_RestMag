package com.zaich.toko_restmag.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaich.toko_restmag.databinding.ItemMenuBinding
import com.zaich.toko_restmag.databinding.ItemUserBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.model.Users

class UserAdapter(private val list:ArrayList<Users>, val context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Users){
            with(binding){
                tvId.text = user.id.toString()
                tvUserName.text = user.user_name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setUser(user: ArrayList<Users>) {
        with(list) {
            clear()
            addAll(user)
        }
        notifyDataSetChanged()
    }

}