package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.UserFavAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFavoritesBinding
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FavoritesFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var userFavAdapter: UserFavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserve()
        initRecyclerView()
    }

    private fun viewModelObserve() {
        viewModel.listUserFavorites.observe(viewLifecycleOwner, { userFav ->
            userFav?.let { userFavAdapter.listUserFav = it }
            userFavAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userFavAdapter = UserFavAdapter()
        binding.rvFavorites.adapter = userFavAdapter
    }


}