package com.externalpods.hcprodemo.presentation.users.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.externalpods.hcprodemo.presentation.databinding.UserItemViewBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import com.externalpods.hcprodemo.presentation.users.list.viewholder.UserViewHolder

class UsersListAdapter: ListAdapter<UserModel, UserViewHolder>(UserDiffCallBack()) {

    var listener: (UserModel) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.bind(getItem(position), listener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}
