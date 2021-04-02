package rpl1pnp.fikri.favoriteapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.favoriteapp.adapter.UserFavAdapter
import rpl1pnp.fikri.favoriteapp.databinding.FragmentMainBinding
import rpl1pnp.fikri.favoriteapp.entity.UserFavorite
import rpl1pnp.fikri.favoriteapp.helper.UserFavoriteHelper
import java.util.*

class MainFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentMainBinding? = null
    private lateinit var userFavAdapter: UserFavAdapter
    private lateinit var userFavoriteList: List<UserFavorite>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentResolver = UserFavoriteHelper(requireActivity())
        swipeRefreshContent(contentResolver)
        userFavoriteList = ArrayList()
        userFavoriteList = getUserList(contentResolver)!!
        initRecyclerView()
    }

    private fun swipeRefreshContent(contentResolver: UserFavoriteHelper) {
        binding.srLayout.setOnRefreshListener {
            getUserList(contentResolver)
            binding.srLayout.isRefreshing = false
        }
    }

    private fun getUserList(
        contentResolver: UserFavoriteHelper
    ): List<UserFavorite>? {
        var userFavoriteList: List<UserFavorite>? = ArrayList()
        try {
            contentResolver.getUser().observe(viewLifecycleOwner, {
                userFavoriteList = it
                userFavAdapter.listUserFav = userFavoriteList!!
                userFavAdapter.notifyDataSetChanged()
                isDataEmpty(userFavoriteList)
            })
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
        }
        return userFavoriteList
    }

    private fun initRecyclerView() {
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        userFavAdapter = UserFavAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    it.id!!
                )
            )
        }
        binding.rvFavorites.adapter = userFavAdapter
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}