package com.dicoding.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.DetailActivity
import com.dicoding.githubuser.adapter.UserAdapter
import com.dicoding.githubuser.database.UserFavoriteEntity
import com.dicoding.githubuser.databinding.FragmentFavoriteBinding
import com.dicoding.githubuser.model.UserModel
import com.dicoding.githubuser.viewmodel.FavoriteViewModel
import java.util.ArrayList

class FavoriteFragment : Fragment() {

    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var _binding: FragmentFavoriteBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUser = binding.rvUser
        showRecyclerView()

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getFavorite()?.observe(viewLifecycleOwner) {
            if (it != null) {
                val userList = addList(it)
                userAdapter.addUser(userList)
            }
        }

        //Intent data to Detail
        userAdapter.onClickItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, it)
            startActivity(intent)
        }
    }

    private fun addList(users: List<UserFavoriteEntity>): ArrayList<UserModel> {
        val listUsers = ArrayList<UserModel>()
        for (user in users) {
            val list = UserModel(
                user.username,
                user.avatar,
                user.id
            )
            listUsers.add(list)
        }
        return listUsers
    }

    private fun showRecyclerView() {
        rvUser.layoutManager = LinearLayoutManager(activity)
        userAdapter = UserAdapter()
        rvUser.adapter = userAdapter

        rvUser.setHasFixedSize(true)
    }

}