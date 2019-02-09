package com.mutualmobile.android.sampleui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mutualmobile.android.sampleui.R

class JobProfileSelectionMini(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var compoundView: View? = null

    init {
        compoundView = LayoutInflater.from(context).inflate(R.layout.view_job_profile_selection_min, this, true)
    }


}