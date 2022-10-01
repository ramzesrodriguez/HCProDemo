package com.externalpods.hcprodemo.presentation.users.details

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.externalpods.hcprodemo.presentation.R
import com.externalpods.hcprodemo.presentation.databinding.FragmentUserDetailsBinding
import com.externalpods.hcprodemo.presentation.users.list.models.UserModel


/**
 * A simple [Fragment] subclass.
 * Use the [UserDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserDetailsFragment : Fragment() {

  private var userModel: UserModel? = null
  private lateinit var binding: FragmentUserDetailsBinding
  private var shortAnimationDuration: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      userModel = it.getParcelable("user_model")
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.userName.text = userModel?.name
    binding.userEmail.text = userModel?.email
    animate()
    binding.fakeView.setOnClickListener {
      findNavController().navigate(R.id.action_userDetailsFragment_to_contactUserFragment)
    }
  }

  private fun animate() {
    val fakeView = binding.fakeView
    //fakeView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_out))
    val fadeIn = AlphaAnimation(0.1f, 0.7f)
    val fadeOut = AlphaAnimation(0.7f, 0.1f)
    val set = AnimationSet(false)
    set.addAnimation(fadeIn)
    set.addAnimation(fadeOut)
    fadeOut.startOffset = 0
    set.duration = 800
    fakeView.startAnimation(set)
    set.setAnimationListener(object : AnimationListener {
      override fun onAnimationStart(animation: Animation) {}
      override fun onAnimationRepeat(animation: Animation) {}
      override fun onAnimationEnd(animation: Animation) {
        fakeView.startAnimation(set)
      }
    })
  }
}