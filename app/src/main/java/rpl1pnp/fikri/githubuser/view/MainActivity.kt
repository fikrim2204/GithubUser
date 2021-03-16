package rpl1pnp.fikri.githubuser.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding
import rpl1pnp.fikri.githubuser.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private var user: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUserGithub.layoutManager = LinearLayoutManager(this)
        initUser()
        adapter = MainAdapter(user) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(USER, it)
            startActivity(intent)
        }
        binding.rvUserGithub.adapter = adapter
    }

    private fun initUser() {
        val userName =
    }

    companion object {
        private const val USER = "user"
    }
}