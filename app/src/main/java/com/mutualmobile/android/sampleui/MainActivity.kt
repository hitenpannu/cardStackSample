package com.mutualmobile.android.sampleui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.View
import com.mutualmobile.android.sampleui.customViews.JobProfileSelectionDropDown
import com.mutualmobile.android.sampleui.models.createDummyProfiles
import com.mutualmobile.android.sampleui.models.getJobProfiles
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_action_buttons.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CardStackListener {

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val stackAdapter by lazy { ProfileStackAdapter(createDummyProfiles().toMutableList()) }
    private val jobProfileDropDown by lazy { findViewById<JobProfileSelectionDropDown>(R.id.job_profile_selection) }
    private val dropDownShadow by lazy { findViewById<View>(R.id.shadow_drop_down) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeStackView()

        jobProfileDropDown.setDropDrownListener(object : JobProfileSelectionDropDown.Companion.DropDownContract {
            override fun onDropDownClose() {
                dropDownShadow.visibility = View.GONE
            }

            override fun onDropDownOpen() {
                dropDownShadow.visibility = View.VISIBLE
            }
        })

        getJobProfiles().let {
            jobProfileDropDown.setSelectedProfile(it.get(Random(0).nextInt(it.size)))
            jobProfileDropDown.setAvailableProfiles(it)
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
        manager.setStackFrom(StackFrom.Bottom)
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
                btn_save.updateRatio(0f)
            }
            Direction.Right -> {
                btn_rejected.updateRatio(0f)
                btn_selected.updateRatio(ratio)
                btn_save.updateRatio(0f)
            }
            Direction.Top -> {
                btn_save.updateRatio(newRatio = ratio)
                btn_selected.updateRatio(0f)
                btn_rejected.updateRatio(0f)
            }
            else -> {
                btn_save.updateRatio(0f)
                btn_selected.updateRatio(0f)
                btn_rejected.updateRatio(0f)
            }
        }
        logError("Card Dragged " + ratio + " Direction " + direction)
    }

    override fun onCardSwiped(direction: Direction?) {
        btn_selected.updateRatio(0f)
        btn_rejected.updateRatio(0f)
        btn_save.updateRatio(0f)
        logError("Card Swipped  Direction " + direction)
        if (direction == Direction.Bottom) {
            cardStackView.rewind()
        }
    }

    override fun onCardCanceled() {
        logError("Card Cancelled Direction ")
        btn_selected.updateRatio(0f)
        btn_rejected.updateRatio(0f)
        btn_save.updateRatio(0f)
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
}
