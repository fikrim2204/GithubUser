package rpl1pnp.fikri.githubuser.view.activity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding

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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            Handler(mainLooper).postDelayed({
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000)
        } else {
            super.onBackPressed()
        }
    }
}