package com.externalpods.hcprodemo.presentation.users.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.externalpods.hcprodemo.presentation.databinding.FragmentUsersListBinding
import com.externalpods.hcprodemo.presentation.users.list.viewmodel.UsersViewModel
import com.externalpods.hcprodemo.presentation.users.list.adapter.UsersListAdapter
import com.externalpods.hcprodemo.presentation.users.list.states.UsersContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {

  private var _binding: FragmentUsersListBinding? = null
  private lateinit var adapter: UsersListAdapter

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val usersViewModel: UsersViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUsersListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initAdapter()
    loadUserList()
    initObservers()
    observerErrors()
    initSearchBar()
  }

  private fun initSearchBar() {
    binding.searchBoxContainer.searchEditText.doOnTextChanged { text, _, _, _ ->
      val query = text.toString().lowercase()
      binding.searchBoxContainer.clearSearchQuery.visibility = if (query.isNotEmpty())
        View.VISIBLE
      else View.GONE

      adapter.filter.filter(query)
    }
    binding.searchBoxContainer.clearSearchQuery.setOnClickListener {
      binding.searchBoxContainer.searchEditText.text?.clear()
    }
  }

  private fun initAdapter() {
    adapter = UsersListAdapter()
    val rv = binding.usersRecyclerView
    rv.adapter = adapter
    rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    rv.itemAnimator = DefaultItemAnimator()
    adapter.listener = {
      // TODO - handle click to do action
    }
  }

  private fun loadUserList() {
    usersViewModel.setEvent(UsersContract.UserEvent.GetAllUsers)
  }

  private fun initObservers() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        usersViewModel.getStateFlow<UsersContract.UserState>()?.collect {
          when (val state = it) {
            is UsersContract.UserState.GetUsersSuccess -> {
              adapter.submitList(state.users)
            }
            is UsersContract.UserState.GetUsersLoading -> {

            }
            else -> {}
          }
        }
      }
    }
  }

  private fun observerErrors() {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        usersViewModel.effect.collect { error ->
          if (error is UsersContract.UserEffect.GetUsersError) {
            Toast.makeText(
              requireContext(), error.error.message, Toast.LENGTH_SHORT
            ).show()
          }
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}