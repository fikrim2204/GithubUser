package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
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

    companion object {
        private const val LOGIN = "login_key"
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
        viewModelObserve(savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapterFollow = FollowAdapter {
            val loginFollowers = it.login
            viewModel.getUserDetail(loginFollowers)
        }
        adapterFollow.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapterFollow
    }

    private fun viewModelObserve(savedInstanceState: Bundle?) {
        viewModel.listResponseFollowers.observe(requireActivity(), { item ->
            if (item != null) {
                followers = item
                adapterFollow.follow = item
                adapterFollow.notifyDataSetChanged()
                if (followers.size != 0) {
                    binding.tvNoFollowers.visibility = View.GONE
                } else {
                    val noFollowers = login + " " + getString(R.string.no_followers)
                    binding.tvNoFollowers.text = noFollowers
                    binding.tvNoFollowers.visibility = View.VISIBLE
                }
            }
        })
        viewModel.selectedItem.observe(requireActivity(), { item ->
            login = item
            if (savedInstanceState == null) {
                viewModel.getFollowers(login)
            } else {
                val savedLogin = savedInstanceState.getString(LOGIN)
                if (savedLogin != login) {
                    viewModel.getFollowers(login)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN, login)
    }
}