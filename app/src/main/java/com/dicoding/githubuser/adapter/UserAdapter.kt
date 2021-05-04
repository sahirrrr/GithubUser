package com.dicoding.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.dicoding.githubuser.model.UserModel

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<UserModel>()
    var onClickItem: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    fun addUser(items: ArrayList<UserModel>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount() = listUser.size

    inner class UserViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(user.avatar)
                        .apply(RequestOptions())
                        .into(imgItemPhoto)
                tvItemName.text = user.username
                tvItemId.text = user.id.toString()
            }
        }

        init {
            itemView.setOnClickListener{
                onClickItem?.invoke(listUser[adapterPosition])
            }
        }
    }
}