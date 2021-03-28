package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentMainBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.utils.viewState
import rpl1pnp.fikri.githubuser.view.activity.DetailActivity
import rpl1pnp.fikri.githubuser.view.activity.MainActivity
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var _binding: FragmentMainBinding? = null
    private var userSingleResponses: List<UserSingleResponse> = mutableListOf()
    private var login: String? = null
    private lateinit var adapter: MainAdapter

    companion object {
        private const val LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivSearch.visibility = View.VISIBLE
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        recyclerView()
        viewModelObserve()
    }

    private fun recyclerView() {
        binding.rvUserGithub.setHasFixedSize(true)
        binding.rvUserGithub.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        adapter = MainAdapter {
            login = it.login
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(LOGIN, login)
            startActivity(intent)
        }
        binding.rvUserGithub.adapter = adapter
    }

    private fun viewModelObserve() {
        viewModel.listResponse.observe(viewLifecycleOwner, { item ->
            notFound(item)
            if (item != null) {
                userSingleResponses = item
                adapter.userSingleResponse = userSingleResponses
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.listResponseFailure.observe(viewLifecycleOwner, { item ->
            if (item != null) {
                Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            searchImage(it)
            binding.loading.visibility = loading(it)
            binding.rvUserGithub.visibility = viewState(it)
        })

        viewModel.failedResponse.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun searchImage(boolean: Boolean) {
        if (boolean) {
            binding.ivSearch.visibility = View.GONE
        }
    }

    private fun notFound(item: List<UserSingleResponse>?) {
        if (item?.size != 0) {
            binding.ivSearch.visibility = View.GONE
            binding.tvNotFound.visibility = View.GONE
        } else {
            binding.ivSearch.visibility = View.VISIBLE
            binding.tvNotFound.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        SettingFragment()
                    ).addToBackStack(SettingFragment::class.java.simpleName)
                }
                return true
            }
            R.id.favorites -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        FavoritesFragment()
                    ).addToBackStack(FavoritesFragment::class.java.simpleName)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}