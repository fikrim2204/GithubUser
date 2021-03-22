package rpl1pnp.fikri.githubuser.view.activity

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()
    private var userSingleResponses: List<UserSingleResponse> = mutableListOf()
    var login: String? = null

    companion object {
        const val EMPTY_QUERY = "empty_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivSearch.visibility = View.VISIBLE

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Github User"
        }
        recyclerView()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.listResponse.observe(this, { item ->
            notFound(item)
            if (item != null) {
                userSingleResponses = item
                adapter.userSingleResponse = userSingleResponses
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.listResponseFailure.observe(this, { item ->
            if (item != null) {
                Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isLoading.observe(this, {
            searchImage(it)
            binding.loading.visibility = loading(it)
        })

        viewModel.failedResponse.observe(this, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun recyclerView() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter {
            login = it.login
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("LOGIN", login)
            startActivity(intent)
        }
        binding.rvUserGithub.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_main, menu)

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
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
                        Log.d("TAG", "${viewModel.getUser(query)}")
                        true
                    } else false
                }
            }
        })
        searchView.setOnCloseListener {
            searchView.setQuery(EMPTY_QUERY, true)
            false
        }
        return super.onCreateOptionsMenu(menu)
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

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}