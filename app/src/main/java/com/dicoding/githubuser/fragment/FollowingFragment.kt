package com.dicoding.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.UserAdapter
import com.dicoding.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var _binding: FragmentFollowingBinding
    private val binding get() = _binding

    companion object {
        const val ARG_USERNAME = "arg_username"

        fun newInstance(username: String?): FollowingFragment {
            val mFragment = FollowingFragment()
            val mBundle = Bundle()
            mBundle.putString(ARG_USERNAME, username)
            mFragment.arguments = mBundle

            return mFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = binding.rvFollowing
        showRecyclerView()
        val login = arguments?.getString(FollowerFragment.ARG_USERNAME)

        // get activity from detail for progressbar
        val progress = activity?.findViewById<ProgressBar>(R.id.progress_bar)
        progress?.visibility = View.VISIBLE

        detailViewModel = ViewModelProvider( this).get(DetailViewModel::class.java)
        detailViewModel.getFollowing(login)
        detailViewModel.getFollowerUser().observe(viewLifecycleOwner, { userDetailModel ->
            if (userDetailModel != null) {
                userAdapter.addUser(userDetailModel)
                progress?.visibility = View.GONE
            }
        })
    }

    private fun showRecyclerView() {
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        userAdapter = UserAdapter()
        rvFollowing.adapter = userAdapter

        rvFollowing.setHasFixedSize(true)
    }
}