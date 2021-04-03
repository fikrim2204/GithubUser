package rpl1pnp.fikri.githubuser.view.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding
import rpl1pnp.fikri.githubuser.view.fragment.MainFragment
import rpl1pnp.fikri.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitOnce: Boolean = false

    companion object {
        private const val EMPTY_QUERY = "empty_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createActionBar()
//        initFragment()
    }

    private fun createActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager
        val mainFragment = MainFragment()
        val fragment = fragmentManager.findFragmentByTag(MainFragment::class.java.simpleName)
        if (fragment !is MainFragment) {
            supportFragmentManager.commit {
                add(
                    R.id.fragment_container,
                    mainFragment,
                    MainFragment::class.java.simpleName
                )
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.top_app_bar_main, menu)
//        val searchManager =
//            getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = resources.getString(R.string.search_hint)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.getUser(query)
//                hideKeyboard()
//                return true
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                return if (query?.length == null) {
//                    false
//                } else {
//                    if (query.length >= 3) {
//                        viewModel.getUser(query)
//                        true
//                    } else false
//                }
//            }
//        })
//        searchView.setOnCloseListener {
//            searchView.setQuery(EMPTY_QUERY, true)
//            false
//        }
//        return true
//    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(
                this,
                R.id.fragment_container
            ).currentDestination?.id == R.id.mainFragment
        ) {
            if (doubleBackToExitOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitOnce = true

            val msg = getString(R.string.confirm_exit)
            //displays a toast message when user clicks exit button
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            //displays the toast message for a while
            Handler(mainLooper).postDelayed({
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000)
        } else {
            super.onBackPressed()
        }
    }
}