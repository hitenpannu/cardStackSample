package com.mutualmobile.android.sampleui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.View
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), CardStackListener {

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val stackAdapter by lazy { ProfileStackAdapter(createSpots().toMutableList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeStackView()

        changeProfile.setOnClickListener {
            if (chipGroup.visibility == View.VISIBLE) {
                chipGroup.visibility = View.GONE
            } else {
                chipGroup.visibility = View.VISIBLE
            }
        }

        ContextCompat.getDrawable(this, R.drawable.ic_checked)?.let {
            btn_selected.setDrawableForIdleMode(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_white_check)?.let {
            btn_selected.setDrawableForProgressMode(it)
        }

        btn_rejected.setIconColor(ContextCompat.getColor(this, R.color.red))

        ContextCompat.getDrawable(this, R.drawable.ic_cancel)?.let {
            btn_rejected.setDrawableForIdleMode(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_white_cancel)?.let {
            btn_rejected.setDrawableForProgressMode(it)
        }

        btn_reload.setIconColor(ContextCompat.getColor(this, R.color.colorReload))
        btn_reload.showCircularBorder()

        ContextCompat.getDrawable(this, R.drawable.ic_refresh)?.let {
            btn_reload.setDrawableForIdleMode(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_white_refresh)?.let {
            btn_reload.setDrawableForProgressMode(it)
        }

        btn_save.setIconColor(ContextCompat.getColor(this, R.color.colorAccent))
        btn_save.showCircularBorder()

        ContextCompat.getDrawable(this, R.drawable.ic_save_profile)?.let {
            btn_save.setDrawableForIdleMode(it)
        }
        ContextCompat.getDrawable(this, R.drawable.ic_white_save_profile)?.let {
            btn_save.setDrawableForProgressMode(it)
        }
    }

    private fun initializeStackView() {
        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)

        cardStackView.layoutManager = manager
        cardStackView.adapter = stackAdapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        logError("Card Disappeared " + position)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        when (direction) {
            Direction.Left -> {
                btn_selected.updateRatio(0f)
                btn_rejected.updateRatio(ratio)
            }
            Direction.Right -> {
                btn_rejected.updateRatio(0f)
                btn_selected.updateRatio(ratio)
            }
            Direction.Top -> {
                btn_selected.updateRatio(0f)
                btn_rejected.updateRatio(0f)
            }
            else -> {
                btn_selected.updateRatio(0f)
                btn_rejected.updateRatio(0f)
            }
        }
        logError("Card Dragged " + ratio + " Direction " + direction)
    }

    override fun onCardSwiped(direction: Direction?) {
        btn_selected.updateRatio(0f)
        btn_rejected.updateRatio(0f)
        logError("Card Swipped  Direction " + direction)
        if (direction == Direction.Bottom) {
            cardStackView.rewind()
        }
    }

    override fun onCardCanceled() {
        logError("Card Cancelled Direction ")
        btn_selected.updateRatio(0f)
        btn_rejected.updateRatio(0f)
    }

    override fun onCardAppeared(view: View?, position: Int) {
        logError("Card Appeared  Direction " + position)
    }

    override fun onCardRewound() {
        logError("Card Dragged rewound")
    }


    private fun logError(message: String) {
        Log.e("STACK_LISTENER", message)
    }

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

    private fun createSpots(): List<ProfilePreview> {
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


}
