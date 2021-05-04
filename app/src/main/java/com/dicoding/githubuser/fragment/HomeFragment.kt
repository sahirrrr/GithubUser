package com.dicoding.githubuser.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.DetailActivity
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.UserAdapter
import com.dicoding.githubuser.databinding.FragmentHomeBinding
import com.dicoding.githubuser.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUser = binding.rvUser
        showRecyclerView()
        searchUser()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.getUser().observe(viewLifecycleOwner, { userModel ->
            if (userModel != null) {
                userAdapter.addUser(userModel)
                showLoading(false)
            }
        })

        // For keeping data while rotate in search
        if (savedInstanceState == null ) {
            mainViewModel.setUser()
        }

        //Intent data to Detail
        userAdapter.onClickItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, it)
            startActivity(intent)
        }

        // Search box clickable
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }
    }

    private fun searchUser() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView


        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                showLoading(false)
                if (query != null) {
                    mainViewModel.setSearchUser(query)
                    showLoading(true)
                } else {
                    Toast.makeText(activity, "Insert Username", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showRecyclerView() {
        rvUser.layoutManager = LinearLayoutManager(activity)
        userAdapter = UserAdapter()
        rvUser.adapter = userAdapter

        rvUser.setHasFixedSize(true)
    }

    // Show and un-show progress bar
    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }
}