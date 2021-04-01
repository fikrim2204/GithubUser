package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.FollowAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowingBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()
    private var following: ArrayList<DataFollow> = arrayListOf()
    private var login: String? = null
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowAdapter

    companion object {
        private const val LOGIN = "login_key"
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
        initRecyclerView()
        viewModelObserve(savedInstanceState)
    }

    private fun viewModelObserve(savedInstanceState: Bundle?) {
        viewModel.listResponseFollowing.observe(viewLifecycleOwner, { item ->
            if (item != null) {
                following = item
                adapter.follow = following
                adapter.notifyDataSetChanged()
                if (following.size != 0) {
                    binding.rvFollowing.visibility = View.VISIBLE
                    binding.tvNoFollowing.visibility = View.GONE
                    binding.ivNoFollowing.visibility = View.GONE
                } else {
                    binding.rvFollowing.visibility = View.GONE
                    val noFollowing = login + " " + getString(R.string.no_following)
                    binding.tvNoFollowing.text = noFollowing
                    binding.tvNoFollowing.visibility = View.VISIBLE
                    binding.ivNoFollowing.visibility = View.VISIBLE
                }
            }
        })
        viewModel.selectedItem.observe(viewLifecycleOwner, { item ->
            login = item
            if (savedInstanceState == null) {
                viewModel.getFollowing(login)
            } else {
                val savedLogin = savedInstanceState.getString(LOGIN)
                if (savedLogin != login) {
                    viewModel.getFollowing(login)
                }
            }
        })
        viewModel.listResponseFailure.observe(viewLifecycleOwner, { item ->
            Toast.makeText(activity, item, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerView() {
        binding.rvFollowing.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = FollowAdapter {
            val loginFollowing = it.login
            viewModel.getUserDetail(loginFollowing)
        }
        binding.rvFollowing.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN, login)
    }
}