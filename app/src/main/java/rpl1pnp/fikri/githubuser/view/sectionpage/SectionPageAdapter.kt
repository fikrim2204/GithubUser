package rpl1pnp.fikri.githubuser.view.sectionpage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import rpl1pnp.fikri.githubuser.view.fragment.FollowFragment

class SectionPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position + 1)
    }

    var login: String? = null
}