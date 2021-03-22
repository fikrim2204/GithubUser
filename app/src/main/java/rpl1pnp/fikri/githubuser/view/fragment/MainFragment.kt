package rpl1pnp.fikri.githubuser.view.fragment

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentMainBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.view.activity.DetailActivity
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()
    private var userSingleResponses: List<UserSingleResponse> = mutableListOf()
    var login: String? = null
    lateinit var sharedPref: SharedPreferences

    companion object {
        const val TAG = "MainFragment"
        const val EMPTY_QUERY = ""
        val PREFS_NAME = "Preferences"
        val LOGIN = "key.login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
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
            notFound(item)
            if (item != null) {
                userSingleResponses = item
                adapter.userSingleResponse = userSingleResponses
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.listResponseFailure.observe(requireActivity(), { item ->
            if (item != null) {
                Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(requireActivity(), {
            searchImage(it)
            binding.loading.visibility = loading(it)
        })

        viewModel.failedResponse.observe(requireActivity(), {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun recyclerView() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter {
            login = it.login
            selectLogin(login)
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra("LOGIN", login)
            startActivity(intent)
//            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(login)
//            sendDataToOtherFragment(login)
//            findNavController().navigate(action)
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
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return if (query?.length == null) {
                    searchView.setQuery(EMPTY_QUERY, true)
                    false
                } else {
                    if (query.length > 3) {
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
        super.onCreateOptionsMenu(menu, inflater)
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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun selectLogin(login: String?) {
        val editor = sharedPref.edit()
        editor.putString(LOGIN, login)
        editor.apply()
    }
}