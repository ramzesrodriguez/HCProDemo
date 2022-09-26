package com.externalpods.hcprodemo.presentation.users.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.externalpods.hcprodemo.presentation.databinding.FragmentUserDetailBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel

class UserDetailFragment: Fragment() {

  private var _binding: FragmentUserDetailBinding? = null
  private val binding get() = _binding!!

  private lateinit var user: UserModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    user = arguments?.getParcelable("user_model") ?: UserModel()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.name.text = user.name
    binding.email.text = user.email?.lowercase()
  }

}