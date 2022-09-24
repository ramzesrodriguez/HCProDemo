package com.externalpods.hcprodemo.presentation.users.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.externalpods.hcprodemo.presentation.databinding.UserItemViewBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel

class UserViewHolder(
    private val view: UserItemViewBinding
) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: UserModel, listener: (UserModel) -> Unit) {
        view.name.text = item.name
        view.description.text = item.email
        view.root.setOnClickListener {
            listener(item)
        }
    }
}
