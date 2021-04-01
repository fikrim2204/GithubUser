package rpl1pnp.fikri.favoriteapp.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.favoriteapp.adapter.UserFavAdapter
import rpl1pnp.fikri.favoriteapp.databinding.ActivityMainBinding
import rpl1pnp.fikri.favoriteapp.entity.UserFavorite
import rpl1pnp.fikri.favoriteapp.helper.UserFavoriteHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userFavAdapter: UserFavAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contentResolver = UserFavoriteHelper(this)
        swipeRefreshContent(contentResolver)
        getUserList(contentResolver)
        initRecyclerView()
    }

    private fun swipeRefreshContent(contentResolver: UserFavoriteHelper) {
        binding.srLayout.setOnRefreshListener {
            getUserList(contentResolver)
            binding.srLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userFavAdapter = UserFavAdapter()
        binding.rvFavorites.adapter = userFavAdapter
    }

    private fun getUserList(
        contentResolver: UserFavoriteHelper
    ): List<UserFavorite>? {
        var userFavoriteList: List<UserFavorite>? = ArrayList()
        try {
            contentResolver.getUser().observe(this, {
                userFavoriteList = it
                userFavAdapter.listUserFav = userFavoriteList!!
                userFavAdapter.notifyDataSetChanged()
                isDataEmpty(userFavoriteList)
            })
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        return userFavoriteList
    }

    private fun isDataEmpty(userFavoriteList: List<UserFavorite>?) {
        if (userFavoriteList!!.isNotEmpty()) {
            binding.rvFavorites.visibility = View.VISIBLE
            binding.ivFavoriteEmpty.visibility = View.GONE
            binding.tvFavoriteEmpty.visibility = View.GONE
        } else {
            binding.rvFavorites.visibility = View.GONE
            binding.ivFavoriteEmpty.visibility = View.VISIBLE
            binding.tvFavoriteEmpty.visibility = View.VISIBLE
        }
    }
}