package com.example.giphyapplication.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import com.example.giphyapplication.R
import com.example.giphyapplication.utils.extension.toPixels
import java.lang.Math.PI

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.loadingViewStyle
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val indicatorRadius: Int
    private val interpolator = SineInterpolator()

    init {
        orientation = HORIZONTAL
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingView,
            defStyleAttr,
            R.style.Widget_Sample_LoadingView
        )
        indicatorRadius =
            a.getDimensionPixelSize(R.styleable.LoadingView_radius, context.toPixels(value = 6))
        val indicatorPadding = a.getDimensionPixelSize(
            R.styleable.LoadingView_indicatorPadding,
            context.toPixels(value = 8)
        )
        val background: Drawable? = a.getDrawable(R.styleable.LoadingView_indicator)
            ?: ResourcesCompat.getDrawable(resources, R.drawable.oval, context.theme)
        a.recycle()
        for (i in 0 until LOADING_COUNT) {
            val view = View(context)
            view.background = background
            addView(view, generateLayoutParams(if (i == 0) 0 else indicatorPadding))
        }
        setWillNotDraw(false)
    }

    private fun generateLayoutParams(left: Int): LayoutParams {
        val radiusPx = context.toPixels(value = indicatorRadius)
        return LayoutParams(indicatorRadius * 2, indicatorRadius * 2).apply {
            topMargin = radiusPx
            bottomMargin = radiusPx
            leftMargin = left
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val currentTime = SystemClock.elapsedRealtime()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val input = (currentTime - i * DELAY) % DURATION / (1f * DURATION)
            val interpolated = interpolator.getInterpolation(input)
            child.translationY = interpolated * indicatorRadius
        }
        super.dispatchDraw(canvas)
        if (alpha > 0) {
            postInvalidate()
        }
    }

    private class SineInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float =
            kotlin.math.sin(input.toDouble() * PI * 2.0).toFloat()
    }

    companion object {
        private const val LOADING_COUNT = 3
        private const val DELAY = 70
        private const val DURATION = 530
    }
}