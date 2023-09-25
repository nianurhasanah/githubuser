package com.dicoding.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.ui.follow.FollowFragment
import com.dicoding.githubuser.utils.Output
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val username = intent.getStringExtra("username") ?: ""

        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Output.Success<*> -> {
                    val user = it.data as DetailUserResponse
                    detailBinding.avatarUser.load(user.avatarUrl) {
                        transformations(CircleCropTransformation())
                    }

                    detailBinding.namaUser.text = user.name
                }

                is Output.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Output.Loading -> {
                    detailBinding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getUserDetail(username)

        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING),
        )
        val titleFragments = mutableListOf(
            getString(R.string.followers), getString(R.string.following)
        )
        val adapter = DetailUseraAdapter(this, fragments)
        detailBinding.viewFolls.adapter = adapter

        TabLayoutMediator(detailBinding.tabFolls, detailBinding.viewFolls){ tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        detailBinding.tabFolls.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0) {
                    viewModel.getFollower(username)
                }else {
                    viewModel.getFollowing(username)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        viewModel.getFollower(username)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}