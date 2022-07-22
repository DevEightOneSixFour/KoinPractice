package com.example.koinpractice.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.koinpractice.R
import com.example.koinpractice.databinding.UserListItemBinding
import com.example.koinpractice.model.GitHubUser

class UserAdapter(private val userList: List<GitHubUser>)
    : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount() = userList.size
}

class UserViewHolder(
    private val binding: UserListItemBinding,
    private val context: Context
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: GitHubUser) {
            binding.tvId.text =
                context.getString(R.string.id_s, user.id.toString())
            binding.tvUsername.text =
                context.getString(R.string.username_s, user.login)
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivAvatar)

            binding.root.setOnClickListener {
                Toast.makeText(context, "Username: ${user.login}", Toast.LENGTH_SHORT).show()
            }
            binding.root.animation =
                AnimationUtils.loadAnimation(
                    binding.root.context,
                    R.anim.row_item_anim
                )
        }
    }