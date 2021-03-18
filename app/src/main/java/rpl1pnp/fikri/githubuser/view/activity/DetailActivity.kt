package rpl1pnp.fikri.githubuser.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.model.User
import rpl1pnp.fikri.githubuser.utils.getId

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(USER)
        initUser()
    }

    private fun initUser() {
        user?.let { binding.civProfile.load(getId(this, it.avatar)) }
        binding.tvNameProfile.text = user?.name
        binding.tvUsernameProfile.text = user?.username
        binding.tvNumberFollower.text = user?.follower.toString()
        binding.tvNumberFollowing.text = user?.following.toString()
        binding.tvLocation.text = user?.location
        binding.tvCompany.text = user?.company
        binding.tvNumberRepository.text = user?.repository.toString()
    }

    companion object {
        private const val USER = "user"
    }
}