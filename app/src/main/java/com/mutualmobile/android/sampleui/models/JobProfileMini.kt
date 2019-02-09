package com.mutualmobile.android.sampleui.models

import java.util.*


fun getJobProfiles(): List<JobProfileMini> {
    return arrayListOf("Full Stack", "Angular Developer", "Android developer", "Spring Developer")
        .map { JobProfileMini(UUID.randomUUID().toString(), it) }
}

data class JobProfileMini(val profileId: String, val profileName: String) {

    fun isSameAs(jobProfileMini: JobProfileMini?): Boolean {
        return this.profileName.toLowerCase() == jobProfileMini?.profileName?.toLowerCase() ?: false
    }
}