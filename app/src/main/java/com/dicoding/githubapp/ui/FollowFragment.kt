package com.dicoding.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.data.response.ItemsItem
import com.dicoding.githubapp.databinding.FragmentFollowBinding
import kotlin.properties.Delegates

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() =_binding!!

    private val viewModel by viewModels<FollowViewModel>()

    private var position by Delegates.notNull<Int>()
    private lateinit var username: String

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1){
            viewModel.followerUser(username)
            viewModel.listFollower.observe(viewLifecycleOwner){
                setFollowData(it)
            }
        } else {
            viewModel.followingUser(username)
            viewModel.listFollowing.observe(viewLifecycleOwner){
                setFollowData(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    private fun setFollowData(users: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        val adapter = FollowAdapter()
        adapter.submitList(users)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}