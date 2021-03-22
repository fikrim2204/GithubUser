package rpl1pnp.fikri.githubuser.view.fragment

import android.content.SharedPreferences
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
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapterFollow: FollowAdapter

    lateinit var sharedPref: SharedPreferences
    private var followers: ArrayList<DataFollow> = arrayListOf()
    private var following: ArrayList<DataFollow> = arrayListOf()
    private val viewModel: DetailViewModel by activityViewModels()
    private var login: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFollowers(login)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserve()
        initRecycler()
        Log.d("TAG", "$login")
    }

    private fun initRecycler() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        adapterFollow = FollowAdapter {
            val loginFollow = it.login
            viewModel.getUserDetail(loginFollow)
        }
        adapterFollow.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapterFollow
    }

    private fun viewModelObserve() {
        Log.d("TAGFollowersFragment", "viewModelObserve : $login")

        viewModel.listResponseFollowers.observe(requireActivity(), { item ->
            if (item != null) {
                followers = item
                adapterFollow.follow = item
                adapterFollow.notifyDataSetChanged()
            }
        })

        viewModel.selectedItem.observe(requireActivity(), {item ->
            login = item
            viewModel.getFollowers(login)
        })
    }
}