package rpl1pnp.fikri.favoriteapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rpl1pnp.fikri.favoriteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}