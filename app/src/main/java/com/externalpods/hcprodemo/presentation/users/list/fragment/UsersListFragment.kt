package com.externalpods.hcprodemo.presentation.users.list.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.externalpods.hcprodemo.presentation.R
import com.externalpods.hcprodemo.presentation.databinding.FragmentUsersListBinding
import com.externalpods.hcprodemo.presentation.users.list.adapter.UsersListAdapter
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private lateinit var adapter: UsersListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersListAdapter()
        val rv = binding.usersRecyclerView
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.setDivider(R.drawable.list_divider)
        rv.itemAnimator = DefaultItemAnimator()
        adapter.submitList(
            listOf(
                UserModel(id = 0, name = "Zero", email = "zero@test.com"),
                UserModel(id = 1, name = "One", email = "one@test.com"),
                UserModel(id = 2, name = "Two", email = "two@test.com")
            )
        )
    }
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}