package com.mutualmobile.android.sampleui.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageButton
import com.mutualmobile.android.sampleui.R
import kotlin.math.min

class CirculaImageButton(context: Context, attributeSet: AttributeSet) : ImageButton(context, attributeSet) {

    private var iconColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)

    private var progressDrawable: Drawable? = null
    private var idleDrawable: Drawable? = null
    private var drawBorder = false

    private var ratio: Float = 0f

    private var backgroundPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.FILL
        this.color = iconColor
    }

    private var borderPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.STROKE
        this.color = iconColor
        this.strokeWidth = 5f
    }

    fun setDrawableForProgressMode(drawable: Drawable) {
        this.progressDrawable = drawable
    }

    fun setDrawableForIdleMode(drawable: Drawable) {
        this.idleDrawable = drawable
        setImageDrawable(idleDrawable)
    }

    fun setIconColor(color: Int) {
        this.iconColor = color
        this.backgroundPaint.color = color
        this.borderPaint.color = color
        requestLayout()
    }

    fun updateRatio(newRatio: Float) {
        if (newRatio > 0f) {
            if (ratio <= 0f) {
                setImageDrawable(progressDrawable)
            }
        } else {
            setImageDrawable(idleDrawable)
        }
        ratio = newRatio
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        val radius = min(width, height).div(2) * ratio
        canvas?.drawCircle(width.div(2f), height.div(2f), radius, backgroundPaint)
        if (drawBorder) {
            val actualRadius = min(width - (paddingLeft + paddingRight), height - (paddingTop + paddingBottom)).div(2f)
            canvas?.drawCircle(width.div(2f), height.div(2f), actualRadius, borderPaint)
        }
        super.onDraw(canvas)
    }

    fun showCircularBorder() {
        drawBorder = true
    }

}