package rpl1pnp.fikri.githubuser.view.activity

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding
import rpl1pnp.fikri.githubuser.utils.Prefs
import rpl1pnp.fikri.githubuser.view.fragment.MainFragment
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: Prefs
    private var darkMode: Boolean? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Github User"
        }

        val fragmentManager = supportFragmentManager
        val mainFragment = MainFragment()
        val fragment = fragmentManager.findFragmentByTag(MainFragment::class.java.simpleName)
        if (fragment !is MainFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mainFragment, MainFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun initTheme() {
        prefs = Prefs(this)
        prefs.darkModePref
        darkMode = prefs.darkModePref
        if (darkMode == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
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
                    searchView.setQuery(MainFragment.EMPTY_QUERY, true)
                    Log.d("TAG", "${viewModel.listResponse.value}")
                    false
                } else {
                    if (query.length >= 3) {
                        viewModel.getUser(query)
                        Log.d("TAG", "${viewModel.getUser(query)}")
                        true
                    } else false
                }
            }
        })
        searchView.setOnCloseListener {
            searchView.setQuery(MainFragment.EMPTY_QUERY, true)
            false
        }

        return true
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