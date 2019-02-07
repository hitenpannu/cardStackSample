package com.mutualmobile.android.sampleui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class ProfileStackAdapter(
    private val profilePreviewList: MutableList<ProfilePreview> = mutableListOf()
) :
    RecyclerView.Adapter<ProfileStackAdapter.ViewHolder>() {

    fun setProfiles(profiles: List<ProfilePreview>, notifyAll: Boolean = true) {
        this.profilePreviewList.clear()
        this.profilePreviewList.addAll(profiles)
        if (notifyAll) notifyDataSetChanged()
    }

    fun getProfiles() = this.profilePreviewList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, p0, false))
    }

    override fun getItemCount(): Int {
        return profilePreviewList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profilePreviewList[position]
        holder.name.text = profile.name
        holder.currentDesignationWithCompany.text = profile.getCurrentPosition()
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, profile.name, Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        var currentDesignationWithCompany: TextView = view.findViewById(R.id.currentDesignationWithCompany)
    }
}