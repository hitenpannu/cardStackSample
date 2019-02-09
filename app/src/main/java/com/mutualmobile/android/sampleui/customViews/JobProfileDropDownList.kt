package com.mutualmobile.android.sampleui.customViews

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.mutualmobile.android.sampleui.R
import com.mutualmobile.android.sampleui.models.JobProfileMini

class JobProfileDropDownList(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var compoundView: View? = null
    private var listOfAvailableJobs = listOf<JobProfileMini>()
    private var selectedProfile: JobProfileMini? = null
    private var contract: Contract? = null

    init {
        compoundView = LayoutInflater.from(context).inflate(R.layout.view_job_profile_drop_down_list, this, true)
    }

    fun addJobProfiles(listOfJobs: List<JobProfileMini>) {
        this.listOfAvailableJobs = listOfJobs
        updateProfileList()
    }

    private fun updateProfileList() {
        compoundView?.findViewById<LinearLayout>(R.id.job_profile_container)?.let { containerView ->

            if (listOfAvailableJobs.isEmpty()) return

            val postJob = containerView.getChildAt(containerView.childCount - 1)

            containerView.removeAllViews()

            listOfAvailableJobs.map { createItemView(it, it.isSameAs(selectedProfile)) }.forEach { jobProfileView ->
                containerView.addView(jobProfileView)
            }

            containerView.addView(postJob)
        }
    }

    fun updateSelection(newSelection: JobProfileMini) {
        this.selectedProfile = newSelection
        updateProfileList()
    }

    private fun createItemView(jobProfileMini: JobProfileMini, isSelected: Boolean): View {
        val miniJobProfileView =
            LayoutInflater.from(context).inflate(R.layout.view_single_job_profile_mini_item, null, false)

        miniJobProfileView.findViewById<AppCompatTextView>(R.id.text_profile_name)?.let {
            it.text = jobProfileMini.profileName
            it.setTypeface(it.typeface, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
        }
        miniJobProfileView.findViewById<ImageView>(R.id.ic_selection_tick)?.let {
            it.visibility = if (isSelected) View.VISIBLE else View.GONE
        }

        miniJobProfileView.setOnClickListener {
            contract?.onProfileClick(jobProfileMini)
        }

        return miniJobProfileView
    }

    fun setContract(contract: Contract) {
        this.contract = contract
    }

    companion object {
        interface Contract {
            fun onProfileClick(jobProfileMini: JobProfileMini)
        }
    }

}