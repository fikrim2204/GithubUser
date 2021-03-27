package rpl1pnp.fikri.githubuser.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.repository.local.AppDatabase
import rpl1pnp.fikri.githubuser.repository.local.DatabaseBuilder
import rpl1pnp.fikri.githubuser.repository.local.DatabaseHelperImpl
import rpl1pnp.fikri.githubuser.repository.local.dao.UserFavoriteDao
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite
import rpl1pnp.fikri.githubuser.utils.ViewModelFactory
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.view.sectionpage.SectionPageAdapter
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private var db: AppDatabase? = null
    private var userFavoriteDao: UserFavoriteDao? = null
    private var login: String? = null
    private var userDetail: UserSingleResponse? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
        private const val LOGIN_KEY = "login_key"
        private const val LOGIN = "login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        checkSavedInstance(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        initBackButton()
        viewPager()
        viewModelObserve()
        favoriteButton()
    }

    private fun checkSavedInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            login = savedInstanceState.getString(LOGIN_KEY)
        } else {
            login = intent.getStringExtra(LOGIN)
            viewModel.getUserDetail(login)
        }
    }

    private fun initBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun viewPager() {
        val sectionPageAdapter = login?.let { SectionPageAdapter(this) }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.appBar.elevation = 0f
    }

    private fun viewModelObserve() {
        viewModel.listResponseDetail.observe(this, { item ->
            userDetail = item
            supportActionBar?.apply {
                title = userDetail?.login
            }
            viewModel.selectItem(userDetail?.login)
            with(binding) {
                civProfile.load(userDetail?.avatar_url)
                tvNameProfile.text = userDetail?.name
                tvUsernameProfile.text = userDetail?.login
                tvNumberFollower.text = userDetail?.followers.toString()
                tvNumberFollowing.text = userDetail?.following.toString()
            }
            if (this.userDetail?.company == null) {
                binding.tvCompany.visibility = View.GONE
            } else {
                binding.tvCompany.visibility = View.VISIBLE
                binding.tvCompany.text = this.userDetail?.company
            }
            binding.tvNumberRepository.text = this.userDetail?.public_repos.toString()
            if (this.userDetail?.location == null) {
                binding.tvLocation.visibility = View.GONE
            } else {
                binding.tvLocation.visibility = View.VISIBLE
                binding.tvLocation.text = this.userDetail?.location
            }
        })
        viewModel.isLoading.observe(this, {
            binding.loading.visibility = loading(it)
        })
        viewModel.listResponseFailure.observe(this, { item ->
            Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)!!))
        ).get(DetailViewModel::class.java)
    }

    private fun favoriteButton() {
        var saved = false
        binding.fabFavorite.show()
        binding.fabFavorite.setOnClickListener {
            if (!saved) {
                val userFavorite = UserFavorite(
                    userDetail?.id,
                    userDetail?.name,
                    userDetail?.login,
                    userDetail?.followers,
                    userDetail?.following,
                    userDetail?.location,
                    userDetail?.company,
                    userDetail?.public_repos
                )
                viewModel.insert(userFavorite)

                saved = true
                val msg = getString(R.string.success_add_to_db)
                binding.fabFavorite.hide()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.fabFavorite.show()
//                Toast.makeText(this, "${userDetail?.name} $msg", Toast.LENGTH_SHORT).show()
            } else if (saved) {
                val userFavorite = UserFavorite(
                    userDetail?.id,
                    userDetail?.name,
                    userDetail?.login,
                    userDetail?.followers,
                    userDetail?.following,
                    userDetail?.location,
                    userDetail?.company,
                    userDetail?.public_repos
                )
                viewModel.delete(userFavorite)
                saved = false
                val msg = getString(R.string.success_delete_from_db)
                binding.fabFavorite.hide()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.fabFavorite.show()
//                Toast.makeText(this, "${userDetail?.name} $msg", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGIN_KEY, userDetail?.login)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        login = savedInstanceState.getString(LOGIN_KEY)
    }
}