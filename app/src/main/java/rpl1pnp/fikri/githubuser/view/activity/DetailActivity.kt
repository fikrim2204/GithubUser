package rpl1pnp.fikri.githubuser.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.model.DataUser

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var users: DataUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        users = intent.getParcelableExtra(USER)
        initUser()
    }

    private fun initUser() {
        users?.let { binding.civProfile.load(it.avatar_url) }
        binding.tvNameProfile.text = users?.name
        binding.tvUsernameProfile.text = users?.login
        binding.tvNumberFollower.text = users?.followers.toString()
        binding.tvNumberFollowing.text = users?.following.toString()
        binding.tvLocation.text = users?.location
        binding.tvCompany.text = users?.company
        binding.tvNumberRepository.text = users?.public_repos.toString()
    }

    companion object {
        private const val USER = "userResponseItems"
    }
}