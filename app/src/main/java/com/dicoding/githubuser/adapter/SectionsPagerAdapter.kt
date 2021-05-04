package com.dicoding.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.fragment.FollowerFragment
import com.dicoding.githubuser.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var username: String? = null

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}