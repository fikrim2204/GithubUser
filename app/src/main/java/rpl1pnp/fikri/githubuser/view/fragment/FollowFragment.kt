package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.FollowersAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentFollowersBinding
import rpl1pnp.fikri.githubuser.model.DataFollow
import rpl1pnp.fikri.githubuser.view.sectionpage.SectionPageAdapter
import rpl1pnp.fikri.githubuser.viewmodel.DetailViewModel

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapterFollow: FollowersAdapter

    //    private lateinit var adapterFollowing: FollowingAdapter
    private var followers: ArrayList<DataFollow> = arrayListOf()
    private var following: ArrayList<DataFollow> = arrayListOf()
    private val viewModel: DetailViewModel by viewModels()
    private var login: String? = null

    companion object {
        private const val ARG_SECTION_PAGES = "section_pages"
        private const val ARG_DATA_FOLLOWING = "following"
        private const val ARG_LOGIN = "login"

        @JvmStatic
        fun newInstance(index: Int) = FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(
                    ARG_SECTION_PAGES, index
                )
                putParcelableArrayList(ARG_DATA_FOLLOWING, following)
                putString(ARG_LOGIN, login)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        adapterFollowing.following = following as ArrayList<DataFollowing>
//        adapterFollowing.notifyDataSetChanged()

        initRecycler()
//        initFollowersRecycler()
//        initFollowingRecycler()
        val sectionPageAdapter = activity?.let { SectionPageAdapter(it) }
        login = sectionPageAdapter?.login
        Log.d("LOGINSKUY", login.toString())
    }

    private fun initRecycler() {
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        adapterFollow = FollowersAdapter {
            val loginFollow = it.login
        }
        binding.rvFollow.adapter = adapterFollow
    }

//    private fun initFollowingRecycler() {
//        adapterFollowing = FollowFragment {
//            login = it.login
//        }
//        binding.rvFollow.adapter = adapterFollowing
//
//    }

    private fun initFollowersRecycler() {
    }

    private fun viewModelObserve() {
//        viewModel.getFollowers(login)
        viewModel.getFollowers("fikrim2204")
//        viewModel.getFollowing(login)
        viewModel.getFollowing("fikrim2204")
        viewModel.listResponseFollow.observe(requireActivity(), { item ->
            if (item != null) {
                followers = item
                adapterFollow.followers = followers
                adapterFollow.notifyDataSetChanged()
            }
        })
        viewModel.listResponseFollowing.observe(requireActivity(), { item ->
            if (item != null) {
                followers = item
                if(followers != null) {
                    val following = arguments?.getParcelableArrayList<DataFollow>(ARG_DATA_FOLLOWING)
                    adapterFollow.followers = following as ArrayList<DataFollow>
                    adapterFollow.notifyDataSetChanged()
                }
            }
        })
    }
}