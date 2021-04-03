package rpl1pnp.fikri.favoriteapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import rpl1pnp.fikri.favoriteapp.R
import rpl1pnp.fikri.favoriteapp.databinding.FragmentDetailBinding
import rpl1pnp.fikri.favoriteapp.entity.UserFavorite
import rpl1pnp.fikri.favoriteapp.helper.UserFavoriteHelper

class DetailFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentDetailBinding? = null
    val args: DetailFragmentArgs by navArgs()
    private var login = 0
    private var userFavorite: UserFavorite? = null
    var saved = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentResolver = UserFavoriteHelper(requireActivity())
        getUserListById(contentResolver)
        favoriteButtonClick(contentResolver)
    }

    private fun favoriteButtonClick(contentResolver: UserFavoriteHelper) {
        binding.fabFavorite.setOnClickListener {
            if (saved) {
                contentResolver.deleteFavorite(userFavorite?.id!!)
                val msg = "Favorite user removed"
                binding.fabFavorite.hide()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.fabFavorite.show()
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
                saved = false
            } else {
                contentResolver.insertFavorite(userFavorite!!)
                val msg = "Favorite user added"
                binding.fabFavorite.hide()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.fabFavorite.show()
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
                saved = true
            }
        }
    }

    private fun getUserListById(
        contentResolver: UserFavoriteHelper
    ) {
        try {
            login = args.userId
            contentResolver.getUserById(login).observe(viewLifecycleOwner, {
                userFavorite = it
                with(binding) {
                    civProfile.load(userFavorite?.image)
                    if (userFavorite?.name == null) {
                        tvNameProfile.visibility = View.GONE
                    } else {
                        tvNameProfile.visibility = View.VISIBLE
                        tvNameProfile.text = userFavorite?.name
                    }
                    tvUsernameProfile.text = userFavorite?.username
                    tvNumberFollower.text = userFavorite?.followers.toString()
                    tvNumberFollowing.text = userFavorite?.following.toString()
                    if (userFavorite?.company == null) {
                        tvCompany.visibility = View.GONE
                    } else {
                        tvCompany.visibility = View.VISIBLE
                        tvCompany.text = userFavorite?.company
                    }
                    if (userFavorite?.location == null) {
                        tvLocation.visibility = View.GONE
                    } else {
                        tvLocation.visibility = View.VISIBLE
                        tvLocation.text = userFavorite?.location
                    }
                    tvNumberRepository.text = userFavorite?.repositories.toString()
                    fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    fabFavorite.show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}