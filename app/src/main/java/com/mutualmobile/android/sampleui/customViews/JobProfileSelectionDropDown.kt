package com.mutualmobile.android.sampleui.customViews

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mutualmobile.android.sampleui.R
import com.mutualmobile.android.sampleui.models.JobProfileMini

class JobProfileSelectionDropDown(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    private var compoundView: View? = null
    private var dropDownContract: DropDownContract? = null
    private var selectionContract: SelectionContract? = null

    private val changeProfileButton by lazy { compoundView?.findViewById<AppCompatImageButton>(R.id.changeProfile) }
    private val jobProfileDropDown by lazy { compoundView?.findViewById<JobProfileDropDownList>(R.id.job_profile_dropdown) }
    private val currentSelectedProfileView by lazy { compoundView?.findViewById<AppCompatTextView>(R.id.textSelectedJobProfile) }
    private val separator by lazy { compoundView?.findViewById<View>(R.id.separator) }

    init {
        compoundView = LayoutInflater.from(context).inflate(R.layout.layout_job_profile_drop_down, this, true)

        changeProfileButton?.setOnClickListener {
            if (jobProfileDropDown == null) return@setOnClickListener

            if (jobProfileDropDown?.visibility == View.GONE) {
                openDropDown()
            } else {
                closeDropDown()
            }
        }

        jobProfileDropDown?.setContract(object : JobProfileDropDownList.Companion.Contract {
            override fun onProfileClick(jobProfileMini: JobProfileMini) {
                selectionContract?.onSelected(jobProfileMini)
                closeDropDown()
                currentSelectedProfileView?.text = jobProfileMini.profileName
                jobProfileDropDown?.updateSelection(jobProfileMini)
            }
        })
    }

    private fun openDropDown() {
        jobProfileDropDown?.visibility = View.VISIBLE
        separator?.visibility = View.VISIBLE
        changeProfileButton?.rotation = 180f
        dropDownContract?.onDropDownOpen()
    }

    fun closeDropDown() {
        changeProfileButton?.rotation = 0f
        jobProfileDropDown?.visibility = View.GONE
        separator?.visibility = View.GONE
        dropDownContract?.onDropDownClose()
    }


    fun setAvailableProfiles(listOfProfiles: List<JobProfileMini>) {
        jobProfileDropDown?.addJobProfiles(listOfProfiles)
    }

    fun setDropDrownListener(dropDownContract: DropDownContract?) {
        this.dropDownContract = dropDownContract
    }

    fun setSelectionListener(selectionContract: SelectionContract) {
        this.selectionContract = selectionContract
    }

    fun setSelectedProfile(selection: JobProfileMini) {
        jobProfileDropDown?.updateSelection(selection)
    }

    companion object {
        interface DropDownContract {
            fun onDropDownOpen()
            fun onDropDownClose()
        }

        interface SelectionContract {
            fun onSelected(jobProfileMini: JobProfileMini)
        }
    }
}