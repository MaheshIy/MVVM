package com.mvvm.kotlin.ui.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.kotlin.R
import com.mvvm.kotlin.Status
import com.mvvm.kotlin.model.MatchesResponse
import com.mvvm.kotlin.ui.adapter.MatchesAdapter
import com.mvvm.kotlin.ui.viewmodel.MatchesViewModel
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.include_shimmer.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MatchesActivity : AppCompatActivity(), ClickUpdate {

    private val matchesViewModel: MatchesViewModel by viewModel()
    private lateinit var matchesAdapter: MatchesAdapter

    override fun onClicked(id: Int, status: Int) {
        super.onClicked(id, status)
        lifecycleScope.launch {
            matchesViewModel.updateClickedItem(id, status)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        list_matches.layoutManager = LinearLayoutManager(this)
        matchesAdapter = MatchesAdapter(MatchesResponse(arrayListOf()), this, this)
        list_matches.adapter = matchesAdapter

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            setupObserver()
        }
    }

    @SuppressLint("ShowToast")
    private fun setupObserver() {

        matchesViewModel.getRepositories.observe(this, Observer { it.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        if (it.data?.results?.size!! > 0) {
                            ReloadList(it.data)
                        } else {
                            CommonDialog(getString(R.string.message_error), getString(R.string.message_internet_connection))
                        }
                        swipe_refresh.isRefreshing = false
                        shimmer_view_container.stopShimmerAnimation()
                        shimmer_view_container.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        CommonDialog(getString(R.string.message_error), getString(R.string.message_something_went_wrong))
                        shimmer_view_container.stopShimmerAnimation()
                        shimmer_view_container.visibility = View.GONE
                        swipe_refresh.isRefreshing = false
                    }

                    Status.LOADING -> {
                        shimmer_view_container.startShimmerAnimation()
                        shimmer_view_container.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun ReloadList(matchesResponse: MatchesResponse) {
        matchesAdapter.apply {
            updateList(matchesResponse)
            notifyDataSetChanged()
            list_matches.scheduleLayoutAnimation()
        }
    }

    private fun CommonDialog(title: String, message: String) {

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton(getString(R.string.action_ok),
                { dialog, which ->
                    dialog.dismiss()
                })
            .setNegativeButton(getString(R.string.action_cancel), null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}