package rpl1pnp.fikri.favoriteapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import rpl1pnp.fikri.favoriteapp.R
import rpl1pnp.fikri.favoriteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createActionBar()
    }

    private fun createActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(
                this,
                R.id.nav_host_fragment
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