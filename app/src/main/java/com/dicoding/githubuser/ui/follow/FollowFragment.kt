package com.dicoding.githubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.Users
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.ui.ListUserGithubAdapter
import com.dicoding.githubuser.ui.detail.DetailUseraAdapter
import com.dicoding.githubuser.ui.detail.DetailViewModel
import com.dicoding.githubuser.utils.Output

class FollowFragment : Fragment() {

    private var frBinding: FragmentFollowBinding? = null
    private val adapter by lazy{
        ListUserGithubAdapter{

        }
    }
    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frBinding = FragmentFollowBinding.inflate(layoutInflater)
        return frBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frBinding?.rvFolls?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when(type) {
            FOLLOWERS -> {
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageOutputFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageOutputFollows)
            }
        }
    }

    private fun manageOutputFollows(state: Output){
        when (state) {
            is Output.Success<*> -> {
                adapter.setData(state.data as MutableList<Users>)
            }
            is Output.Error -> {
                Toast.makeText(requireActivity(), state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Output.Loading -> {
                frBinding?.progressBar?.isVisible = state.isLoading
            }
        }
    }
    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }

    }
}