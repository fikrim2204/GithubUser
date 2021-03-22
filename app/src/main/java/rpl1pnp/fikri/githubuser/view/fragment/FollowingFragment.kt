package rpl1pnp.fikri.githubuser.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.FollowAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowingBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowAdapter
    private var following: ArrayList<DataFollow> = arrayListOf()
    private val viewModel: DetailViewModel by activityViewModels()
    private var login: String? = null
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFollowing(login)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObserve()
        recyclerView()
    }

    private fun viewModelObserve() {
        viewModel.listResponseFollowing.observe(requireActivity(), { item ->
            if (item != null) {
                following = item
                adapter.follow = following
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.selectedItem.observe(requireActivity(), { item ->
            login = item
            viewModel.getFollowing(login)
        })
    }

    private fun recyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = FollowAdapter {
            val loginFollowing = it.login
            viewModel.getUserDetail(loginFollowing)
        }
        binding.rvFollowing.adapter = adapter
    }
}