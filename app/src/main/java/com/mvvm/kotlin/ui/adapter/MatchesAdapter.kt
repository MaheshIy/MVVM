package com.mvvm.kotlin.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm.kotlin.R
import com.mvvm.kotlin.model.MatchesResponse
import com.mvvm.kotlin.ui.viewmodel.MatchesViewModel
import com.mvvm.kotlin.ui.views.ClickUpdate
import com.mvvm.kotlin.ui.views.MatchesActivity

class MatchesAdapter(private var matchesResponse: MatchesResponse, private val context: Context,
    val clickUpdater: ClickUpdate) : RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    var clickUpdate: ClickUpdate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_matches, parent, false)
        return MatchesViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {

        clickUpdate = clickUpdater

        Glide.with(context)
            .load(matchesResponse.results.get(position).picture.large)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.img_profile_pic)

        holder.txt_name.text = matchesResponse.results.get(position).name.first + " " + matchesResponse.results.get(position).name.last
        holder.txt_age.text = matchesResponse.results.get(position).dob.age.toString()
        holder.txt_city.text = matchesResponse.results.get(position).location.city

        when (matchesResponse.results.get(position).status) {
            0 -> {
                holder.txt_action_message.visibility = View.GONE
                holder.group_btn.visibility = View.VISIBLE
            }
            1 -> {
                GroupVisibiliy(holder, context.getString(R.string.message_member_have_accepted))
            }
            2 -> {
                GroupVisibiliy(holder, context.getString(R.string.message_member_have_declined))
            }
        }

        holder.btn_accept.setOnClickListener {
            GroupVisibiliy(holder, context.getString(R.string.message_member_have_accepted))
            clickUpdate!!.onClicked(position + 1, 1)
            matchesResponse.results.get(position).status = 1
        }

        holder.btn_decline.setOnClickListener {
            GroupVisibiliy(holder, context.getString(R.string.message_member_have_declined))
            clickUpdate!!.onClicked(position + 1, 2)
            matchesResponse.results.get(position).status = 2
        }
    }

    private fun GroupVisibiliy(holder: MatchesViewHolder, actionText: String) {
        holder.group_btn.visibility = View.GONE
        holder.txt_action_message.visibility = View.VISIBLE
        holder.txt_action_message.text = actionText
    }

    override fun getItemCount(): Int = matchesResponse.results.size

    fun updateList(response: MatchesResponse) {
        this.matchesResponse = response
    }

    class MatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_profile_pic: ImageView = itemView.findViewById(R.id.img_profile_pic)
        val txt_name: TextView = itemView.findViewById(R.id.txt_name)
        val txt_age: TextView = itemView.findViewById(R.id.txt_age)
        val txt_city: TextView = itemView.findViewById(R.id.txt_city)
        val btn_accept: Button = itemView.findViewById(R.id.btn_accept)
        val btn_decline: Button = itemView.findViewById(R.id.btn_decline)
        val txt_action_message: TextView = itemView.findViewById(R.id.txt_action_message)
        val group_btn: Group = itemView.findViewById(R.id.group_btn)
    }
}