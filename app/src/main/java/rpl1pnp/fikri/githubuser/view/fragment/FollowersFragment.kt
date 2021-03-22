package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.FollowAdapter
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowersBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var adapterFollow: FollowAdapter

    lateinit var sharedPref: SharedPreferences
    private var followers: ArrayList<DataFollow> = arrayListOf()
    private var following: ArrayList<DataFollow> = arrayListOf()
    private val viewModel: DetailViewModel by viewModels()
    private var login: String? = null

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val PREFS_NAME = "Preferences"
        const val LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref =
            requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        login = getLogin()
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
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        adapterFollow = FollowAdapter {
            val loginFollow = it.login
        }
        adapterFollow.notifyDataSetChanged()
        binding.rvFollow.adapter = adapterFollow
    }

    private fun initFollowersRecycler() {
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
    }

    private fun getLogin(): String? = sharedPref.getString(LOGIN, null)
}