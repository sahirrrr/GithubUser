package com.dicoding.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.model.UserModel
import com.dicoding.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private var userModel: UserModel? = null
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent data from MainActivity
        userModel = intent.getParcelableExtra(EXTRA_USER)
        val login = userModel?.username.toString()
        val id = userModel?.id
        val avatar = userModel?.avatar

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getUser().observe(this, { userDetailModel ->
            showLoading(false)
            if (userDetailModel != null) {
                binding.tvItemUserid.text = userDetailModel.username
                Glide.with(this@DetailActivity)
                        .load(userDetailModel.avatar)
                        .apply(RequestOptions())
                        .into(binding.imgItemPhoto)
                binding.tvItemFollowerNumber.text = userDetailModel.followers.toString()
                binding.tvItemFollowingNumber.text = userDetailModel.following.toString()
                binding.tvItemLocation.text = userDetailModel.location
                binding.tvItemCompany.text = userDetailModel.company
                binding.tvItemRepositoryNumber.text = userDetailModel.repository.toString()
            }
        })
        detailViewModel.userDetail(login)

        // Return back icon
        val returnDetail: ImageView = binding.imgItemReturn
        returnDetail.setOnClickListener(this)

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.btnFavorite.isChecked = true
                        isFavorite = true
                    } else {
                        binding.btnFavorite.isChecked = false
                        isFavorite = false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                detailViewModel.addToFavorite(login, avatar, id)
            } else {
                detailViewModel.deleteFromFavorite(id)
            }
            binding.btnFavorite.isChecked = isFavorite
        }
        sectionPager()
    }

    // Function for tab layout fragment follower and following
    private fun sectionPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userModel?.username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_item_return -> finish()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }
}