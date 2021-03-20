package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.FragmentDetailBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse
import rpl1pnp.fikri.githubuser.utils.loading
import rpl1pnp.fikri.githubuser.view.sectionpage.SectionPageAdapter
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private var login: String? = null
    private var userDetail: UserSingleResponse? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_followers, R.string.tab_following)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login = args.login
        toolbar()
        viewModel.getUserDetail(login)
        viewPager()
        viewModelObserve()
    }

    private fun toolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = login
        }
    }

    private fun viewPager() {
        val sectionPageAdapter = activity?.let { SectionPageAdapter(it) }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        sectionPageAdapter?.login = login
        Log.d("HMM", sectionPageAdapter?.login.toString())
        binding.appBar.elevation = 0f
    }

    private fun viewModelObserve() {
        viewModel.isLoading.observe(requireActivity(), { item ->
            binding.loading.visibility = loading(item)
        })
        viewModel.listResponseDetail.observe(requireActivity(), { userDetail ->
            this.userDetail = userDetail
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
        viewModel.codeError.observe(requireActivity(), { item ->
            Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}