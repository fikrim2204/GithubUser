package rpl1pnp.fikri.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.ActivityMainBinding
import rpl1pnp.fikri.githubuser.model.User
import rpl1pnp.fikri.githubuser.utils.getJsonDataFromAsset

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private var user: List<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
        initUser()
        setAdapter()
        Log.i(TAG, "$user")
    }

    private fun setAdapter() {
        adapter = MainAdapter(user) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(USER, it)
            startActivity(intent)
        }
        binding.rvUserGithub.adapter = adapter
    }

    private fun initRecycler() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(this)
    }

    private fun initUser() {
        val jsonFromString = getJsonDataFromAsset(this, FILE_NAME)
        jsonFromString?.let { Log.i(TAG, it) }
        val jsonType = object : TypeToken<List<User>>() {}.type

        user = Gson().fromJson(jsonFromString, jsonType)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val USER = "user"
        private const val FILE_NAME = "githubuser.json"
    }
}