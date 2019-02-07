package com.mutualmobile.android.sampleui

import java.util.*

data class ProfilePreview(
    val id: Long,
    val name: String,
    val currentLocation: String,
    val profilePic: String,
    val experiences: List<Experience> = listOf(),
    val education: List<Education> = listOf(),
    val expectedMinPackage: Long,
    val expectedMaxPackage: Long,
    val isInterestedInJob: Boolean
) {

    fun getCurrentPosition(): String? {
        return experiences.filter { it.endingDate == null }?.firstOrNull()?.toString()
    }

    fun getTotalWorkingExperienceInMilli(): Long {
        return experiences.map { it.getTenureInCompanyInMIlli() }.sum()
    }

}

data class Experience(
    val companyName: String,
    val designation: String,
    val startingDate: Date,
    val endingDate: Date? = null
) {
    fun getTenureInCompanyInMIlli(): Long {
        return if (endingDate == null) {
            System.currentTimeMillis().minus(startingDate.time)
        } else {
            endingDate.time.minus(startingDate.time)
        }
    }

    override fun toString(): String {
        return "$designation @ $companyName"
    }
}

data class Education(
    val authority: String,
    val course: String,
    val startingDate: Date,
    val endingDate: Date? = null
) {
    fun getCourseDurationInMIlli(): Long {
        return if (endingDate == null) {
            System.currentTimeMillis().minus(startingDate.time)
        } else {
            endingDate.time.minus(startingDate.time)
        }
    }
}

