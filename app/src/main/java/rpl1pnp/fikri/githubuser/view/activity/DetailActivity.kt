package rpl1pnp.fikri.githubuser.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.ActivityDetailBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.view.sectionpage.SectionPageAdapter
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private var login: String? = null
    private var userDetail: UserSingleResponse? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_followers, R.string.tab_following)
        private const val LOGIN_KEY = "login_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSavedInstance(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        initBackButton()
        viewPager()
        viewModelObserve()
    }

    private fun checkSavedInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            login = intent.getStringExtra("LOGIN")
            viewModel.getUserDetail(login)
        } else {
            login = savedInstanceState.getString(LOGIN_KEY)
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
            binding.civProfile.load(this.userDetail?.avatar_url)
            binding.tvNameProfile.text = this.userDetail?.name
            binding.tvUsernameProfile.text = this.userDetail?.login
            binding.tvNumberFollower.text = this.userDetail?.followers.toString()
            binding.tvNumberFollowing.text = this.userDetail?.following.toString()
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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LOGIN_KEY, userDetail?.login)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        login = savedInstanceState.getString(LOGIN_KEY)
        super.onRestoreInstanceState(savedInstanceState)
    }
}