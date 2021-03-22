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
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var login: String? = null
    private var userDetail: UserSingleResponse? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_followers, R.string.tab_following)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login = intent.getStringExtra("LOGIN")
        setSupportActionBar(binding.toolbar)
        viewPager()
        supportActionBar?.apply {
            title = login
        }
        viewModel.getUserDetail(login)
        viewModel.listResponseDetail.observe(this, { item ->
            userDetail = item
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
}