package com.mutualmobile.android.sampleui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

class BackgroundIntersectedView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    val paint by lazy {
        Paint()
            .apply {
                this.color = ContextCompat.getColor(context, R.color.colorPrimary)
                this.style = Paint.Style.FILL
                this.isAntiAlias = true
            }
    }

    var path: Path = Path()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        path.moveTo(left.toFloat(), top.toFloat())
        path.lineTo(left.toFloat(), bottom.toFloat())
        val bottomRight = bottom.minus(100).toFloat()
        path.lineTo(right.toFloat(), bottomRight)
        path.lineTo(right.toFloat(), top.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

}