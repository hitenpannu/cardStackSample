package com.mutualmobile.android.sampleui.models

import java.util.*


private fun getExperience(): List<Experience> {
    return listOf(
        Experience(
            companyName = "Digi World",
            designation = "Sales & BD Executive",
            startingDate = Date().apply { this.time = System.currentTimeMillis().minus(100 * 1000) },
            endingDate = null
        )
    )
}

private fun getEducation(): List<Education> {
    return listOf(
        Education(
            authority = "St. Paul's University",
            course = "M.B.A- Mass Comm & Sales",
            startingDate = Date().apply {
                this.time = System.currentTimeMillis().minus(100 * 1000)
                this.time -= time - 40000
            },
            endingDate = Date().apply { this.time = System.currentTimeMillis().minus(100 * 1000) }
        )
    )
}

fun createDummyProfiles(): List<ProfilePreview> {
    val sampleProfiles = ArrayList<ProfilePreview>()
    sampleProfiles.add(
        ProfilePreview(
            id = 0,
            name = "Yasaka Shrine",
            currentLocation = "Kyoto",
            profilePic = "",
            expectedMinPackage = 300000,
            expectedMaxPackage = 600000,
            isInterestedInJob = true,
            education = getEducation(),
            experiences = getExperience()
        )
    )
    sampleProfiles.add(
        ProfilePreview(
            id = 1,
            name = "Hitender Pannu",
            currentLocation = "Hyderabad",
            profilePic = "",
            expectedMinPackage = 800000,
            expectedMaxPackage = 1100000,
            isInterestedInJob = false,
            education = getEducation(),
            experiences = getExperience()
        )
    )
    sampleProfiles.add(
        ProfilePreview(
            id = 2,
            name = "Niharika Chaudhary",
            currentLocation = "Chandigarh",
            profilePic = "",
            expectedMinPackage = 1100000,
            expectedMaxPackage = 1400000,
            isInterestedInJob = false,
            education = getEducation(),
            experiences = getExperience()
        )
    )
    sampleProfiles.add(
        ProfilePreview(
            id = 2,
            name = "Niviea Chaudhary",
            currentLocation = "Una",
            profilePic = "",
            expectedMinPackage = 200000,
            expectedMaxPackage = 500000,
            isInterestedInJob = false,
            education = getEducation(),
            experiences = getExperience()
        )
    )
    return sampleProfiles
}

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

