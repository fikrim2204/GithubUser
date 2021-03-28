package rpl1pnp.fikri.githubuser.view.fragment

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
        private const val EMPTY_QUERY = "empty_query"
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_main, menu)
        val searchManager =
            activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return if (query?.length == null) {
                    false
                } else {
                    if (query.length >= 3) {
                        viewModel.getUser(query)
                        true
                    } else false
                }
            }
        })
        searchView.setOnCloseListener {
            searchView.setQuery(EMPTY_QUERY, true)
            false
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
            R.id.about -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        AboutFragment()
                    ).addToBackStack(AboutFragment::class.java.simpleName)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}