package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.adapter.UserFavAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFavoritesBinding
import rpl1pnp.fikri.githubuser.repository.local.DatabaseBuilder
import rpl1pnp.fikri.githubuser.repository.local.DatabaseHelperImpl
import rpl1pnp.fikri.githubuser.utils.ViewModelFactory
import rpl1pnp.fikri.githubuser.view.activity.DetailActivity
import rpl1pnp.fikri.githubuser.view.activity.MainActivity
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var userFavAdapter: UserFavAdapter

    companion object {
        private const val LOGIN = "login_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        (activity as MainActivity).supportActionBar?.title = "Favorite User"
        setupViewModel()
        initRecyclerView()
        viewModelObserve()
        binding.srLayout.setOnRefreshListener { viewModel.getUserOnDb() }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(DatabaseHelperImpl(DatabaseBuilder.getInstance(requireActivity().applicationContext)))
        ).get(DetailViewModel::class.java)
    }

    private fun initRecyclerView() {
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userFavAdapter = UserFavAdapter {
            val login = it.username
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(LOGIN, login)
            startActivity(intent)
        }
        binding.rvFavorites.adapter = userFavAdapter
    }

    private fun viewModelObserve() {
        viewModel.listFavoriteUser.observe(viewLifecycleOwner, { userFav ->
            if (userFav.isEmpty()) {
                with(binding) {
                    rvFavorites.visibility = View.GONE
                    ivFavoriteEmpty.visibility = View.VISIBLE
                    tvFavoriteEmpty.visibility = View.VISIBLE
                }
            } else {
                userFav.let { userFavAdapter.listUserFav = it }
                with(binding) {
                    rvFavorites.visibility = View.VISIBLE
                    ivFavoriteEmpty.visibility = View.GONE
                    tvFavoriteEmpty.visibility = View.GONE
                }
            }
            userFavAdapter.notifyDataSetChanged()
            binding.srLayout.isRefreshing = false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        SettingFragment()
                    ).addToBackStack(SettingFragment::class.java.simpleName)
                }
                return true
            }
            R.id.about -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        AboutFragment()
                    ).addToBackStack(AboutFragment::class.java.simpleName)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserOnDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}