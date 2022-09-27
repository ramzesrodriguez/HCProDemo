package com.externalpods.hcprodemo.presentation.users.list.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.externalpods.hcprodemo.presentation.R
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
  private lateinit var searchView: SearchView

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val usersViewModel: UsersViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUsersListBinding.inflate(inflater, container, false)
    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initAdapter()
    loadUserList()
    initObservers()
    initCallbackListener()
    observerErrors()
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

  private fun initCallbackListener() {
    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if (!searchView.isIconified) {
          searchView.onActionViewCollapsed()
        } else {
          if (findNavController().previousBackStackEntry == null)
            requireActivity().finish()
          else
            findNavController().navigateUp()
        }
      }
    }
    requireActivity().onBackPressedDispatcher.addCallback(callback)
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

  override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    menuInflater.inflate(R.menu.search_menu, menu)
    val searchItem = menu.findItem(R.id.action_search)
    if (searchItem != null) {
      searchView = searchItem.actionView as SearchView
      searchView.maxWidth = Integer.MAX_VALUE
      searchView.queryHint = getString(R.string.search_names_hint)
      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          adapter.filter.filter(query)
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          adapter.filter.filter(newText)
          return false
        }
      })
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}