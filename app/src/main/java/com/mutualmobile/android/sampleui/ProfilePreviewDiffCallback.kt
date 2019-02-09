package com.mutualmobile.android.sampleui

import android.support.v7.util.DiffUtil
import com.mutualmobile.android.sampleui.models.ProfilePreview

class ProfilePreviewDiffCallback(
    private val old: List<ProfilePreview>,
    private val new: List<ProfilePreview>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }
}