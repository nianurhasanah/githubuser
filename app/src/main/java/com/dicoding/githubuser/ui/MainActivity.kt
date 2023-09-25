package com.dicoding.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.Users
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.detail.DetailActivity
import com.dicoding.githubuser.utils.Output


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val userAdapter by lazy {
        ListUserGithubAdapter{ user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", user.login)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.listUser.layoutManager = LinearLayoutManager(this)
        mainBinding.listUser.setHasFixedSize(true)
        mainBinding.listUser.adapter = userAdapter

        mainBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.resultUser.observe(this){
            when (it) {
                is Output.Success<*> -> {
                    userAdapter.setData(it.data as MutableList<Users>)
                }
                is Output.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Output.Loading -> {
                    mainBinding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUserList()

    }
}