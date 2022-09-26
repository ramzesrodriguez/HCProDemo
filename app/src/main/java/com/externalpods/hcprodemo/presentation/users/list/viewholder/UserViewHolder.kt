package com.externalpods.hcprodemo.presentation.users.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.externalpods.hcprodemo.presentation.databinding.UserItemViewBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import com.externalpods.hcprodemo.presentation.utils.text.TextToDrawable

class UserViewHolder(
  private val view: UserItemViewBinding
) : RecyclerView.ViewHolder(view.root) {
  fun bind(item: UserModel, listener: (UserModel) -> Unit) {
    view.textViewName.text = item.name
    view.textViewAddress.text = item.address?.city
    view.textViewEmail.text = item.email?.lowercase()
    item.name?.let {
      TextToDrawable.with(view.root.context)
        .text(it)
        .into(view.userImage)
    }
    view.root.setOnClickListener {
      listener(item)
    }
  }
}
