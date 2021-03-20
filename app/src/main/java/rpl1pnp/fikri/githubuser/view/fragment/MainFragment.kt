package rpl1pnp.fikri.githubuser.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    companion object {
        const val TAG = "MainFragment"
        const val EMPTY_QUERY = ""
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
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Github User"
        }
        recyclerView()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.listResponse.observe(requireActivity(), { item ->
            if (item != null) {
                users = item
                adapter.user = users
                adapter.notifyDataSetChanged()
                searchImage()
            }
        })

        viewModel.listResponseFailure.observe(requireActivity(), { item ->
            if (item != null) {
                Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(requireActivity(), {
            binding.loading.visibility = loading(it)
        })

        viewModel.failedResponse.observe(requireActivity(), {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun recyclerView() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter {
            val login = it.login
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(login)
            findNavController().navigate(action)
        }
        binding.rvUserGithub.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_main, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                return if (query?.length == null) {
//                    searchView.setQuery(EMPTY_QUERY, true)
//                    false
//                } else {
//                    if (query.length % 3 == 0) {
//                        viewModel.getUser(query)
//                        true
//                    } else false
//                }
                return false
            }
        })
        searchView.setOnCloseListener {
            searchView.setQuery(EMPTY_QUERY, true)
            false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchImage() {
        if (users.isNotEmpty()) {
            binding.ivSearch.visibility = View.GONE
        } else {
            binding.ivSearch.visibility = View.VISIBLE
        }
    }
}