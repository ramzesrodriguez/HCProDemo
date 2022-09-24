package com.externalpods.hcprodemo.presentation.users.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel

class UserDiffCallBack: DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

}