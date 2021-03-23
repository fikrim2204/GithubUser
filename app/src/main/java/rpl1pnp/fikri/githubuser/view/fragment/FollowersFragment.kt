package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.FollowAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowersBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowersFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()
    private var followers: ArrayList<DataFollow> = arrayListOf()
    private var login: String? = null
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapterFollow: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModelObserve()
        initRecycler()
        Log.d("TAG", "$login")
    }

    private fun initRecycler() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        adapterFollow = FollowAdapter {
            val loginFollowers = it.login
            viewModel.getUserDetail(loginFollowers)
        }
        adapterFollow.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapterFollow
    }

    private fun viewModelObserve() {
        viewModel.listResponseFollowers.observe(requireActivity(), { item ->
            if (item != null) {
                followers = item
                adapterFollow.follow = item
                adapterFollow.notifyDataSetChanged()
            }
        })
        viewModel.selectedItem.observe(requireActivity(), { item ->
            login = item
            viewModel.getFollowers(login)
        })
    }
}