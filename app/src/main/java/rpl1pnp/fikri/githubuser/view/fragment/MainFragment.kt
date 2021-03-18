package rpl1pnp.fikri.githubuser.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentMainBinding
import rpl1pnp.fikri.githubuser.model.DataUser
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()
    private var users: List<DataUser> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUser("fikrim2204")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.title = "Github Search"
        recycler()

        viewModel.listResponse.observe(requireActivity(), { item ->
            viewModel.getUserDetail("fikrim2204")
            if (item != null) {
                users = item
                adapter.user = users
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.listResponseDetail.observe(requireActivity(), {item ->
            if (item != null) {

            }
        })

        viewModel.isLoading.observe(requireActivity(), {
            binding.loading.visibility = loading(it)
        })

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun recycler() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter {
        }
        binding.rvUserGithub.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
                return false
            }
        })
    }

}