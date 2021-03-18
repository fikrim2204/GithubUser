package rpl1pnp.fikri.githubuser.view.sectionpage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import rpl1pnp.fikri.githubuser.view.fragment.FollowersFragment
import rpl1pnp.fikri.githubuser.view.fragment.FollowingFragment

class SectionPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}