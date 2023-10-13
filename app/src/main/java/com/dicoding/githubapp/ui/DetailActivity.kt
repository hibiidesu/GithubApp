package com.dicoding.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.dicoding.githubapp.R
import com.dicoding.githubapp.data.response.DetailUserResponse
import com.dicoding.githubapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f

        val dataUser = intent.getStringExtra(EXTRA_USER)

        if (dataUser != null) {
            detailViewModel.detailUser(dataUser)
        }

        detailViewModel.userData.observe(this){
            setDetailData(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailData(user: DetailUserResponse) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivProfile)
        binding.tvName.text = user.name
        binding.tvLogin.text = user.login

        val follower = "${user.followers} followers"
        val following = "${user.following} following"
        binding.tvFollower.text = follower
        binding.tvFollowing.text = following

        val detailPagerAdapter = DetailPagerAdapter(this)
        detailPagerAdapter.username = user.login
        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}