package com.externalpods.hcprodemo.presentation.users.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.externalpods.hcprodemo.presentation.databinding.UserItemViewBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel
import com.externalpods.hcprodemo.presentation.users.list.viewholder.UserViewHolder

class UsersListAdapter : ListAdapter<UserModel, UserViewHolder>(UserDiffCallBack()), Filterable {

  var listener: (UserModel) -> Unit = { _ -> }
  private var originalList: List<UserModel> = currentList.toList()

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

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(constraint: CharSequence?): FilterResults {
        return FilterResults().apply {
          values = if (constraint.isNullOrEmpty())
            originalList
          else
            onFilter(originalList, constraint.toString())
        }
      }

      @Suppress("UNCHECKED_CAST")
      override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        submitList(results?.values as? List<UserModel>, true)
      }
    }
  }

  override fun submitList(list: List<UserModel>?) {
    submitList(list, false)
  }

  fun onFilter(list: List<UserModel>, constraint: String): List<UserModel> {
    return list.filter {
      it.name?.lowercase()?.contains(constraint.lowercase()) == true
    }
  }

  /**
   * This function is responsible for maintaining the
   * actual contents for the list for filtering
   * The submitList for parent class delegates false
   * so that a new contents can be set
   * While a filter pass true which make sure original list
   * is maintained
   *
   * @param filtered True if the list was updated using filter interface
   * */
  private fun submitList(list: List<UserModel>?, filtered: Boolean) {
    if (!filtered)
      originalList = list ?: listOf()

    super.submitList(list)
  }
}
