package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.FollowAdapter
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowingBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var adapter: FollowAdapter
    private var following: ArrayList<DataFollow> = arrayListOf()
    private val viewModel: DetailViewModel by viewModels()
    private var login: String? = null
    lateinit var sharedPref: SharedPreferences

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

        viewModel.listResponseFollowing.observe(requireActivity(), { item ->
            if (item != null) {
                following = item
                adapter.follow = following
                adapter.notifyDataSetChanged()
            }
        })
        recyclerView()
    }

    private fun recyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = FollowAdapter {
            val login = it.login
        }
        binding.rvFollowing.adapter = adapter
    }

    private fun getLogin(): String? = sharedPref.getString(LOGIN, null)
}