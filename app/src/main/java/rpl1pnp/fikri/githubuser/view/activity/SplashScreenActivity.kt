package rpl1pnp.fikri.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import rpl1pnp.fikri.githubuser.utils.Prefs

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme()
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initTheme() {
        prefs = Prefs(this)
        prefs.darkModePref
        val darkMode = prefs.darkModePref
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}