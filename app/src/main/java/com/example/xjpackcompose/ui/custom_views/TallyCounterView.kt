package com.example.xjpackcompose.ui.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.xjpackcompose.R
import java.util.*
import kotlin.math.roundToInt

//  based on: https://academy.realm.io/posts/360andev-huyen-tue-dao-measure-layout-draw-repeat-custom-views-and-viewgroups-android/
class TallyCounterView : View, ITallyCounter {

    private var count: Int = 0
    private var displayCount: String = ""
    private var cornerRadius: Float = 0.0f
    private lateinit var bgRect: RectF
    private lateinit var numPaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var bgPaint: Paint
    private val MAX_COUNT_VALUE = 9999
    private val MAX_COUNT_STRING = "9999"

    //  create new from code
    constructor(context: Context) : super(context) {
        init()
    }

    //  create new from XML
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    //  create new from XML with style from theme attribute
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.colorAccent)
            strokeWidth = 2f
        }
        numPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, android.R.color.white)
            textSize = (64f * resources.displayMetrics.scaledDensity)
        }
        bgRect = RectF()
        cornerRadius = (2f * resources.displayMetrics.density)
        setCount(0)
    }

    override fun reset() {
        setCount(0)
    }

    override fun increment() {
        setCount(count = count + 1)
    }

    override fun getCount(): Int = count

    override fun setCount(count: Int) {
        this.count = count.coerceAtMost(MAX_COUNT_VALUE)
        this.displayCount = String.format(Locale.ROOT, "%04d", count)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val canvasWidth = width.toFloat()
        val canvasHeight = height.toFloat()

        //  calculate hori center
        val centerX = canvasWidth * .5f

        //  draw bg
        bgRect.set(0f, 0f, canvasWidth, canvasHeight)
        canvas.drawRoundRect(bgRect, cornerRadius, cornerRadius, bgPaint)

        //  draw baseline
        val baselineY = canvasHeight * .6f
        canvas.drawLine(0f, baselineY, canvasWidth, baselineY, linePaint)

        //  measure width of text to display
        val textWidth = numPaint.measureText(displayCount)
        //  x that will center the text in canvas
        val textX = centerX - textWidth * .5f
        //  draw text
        canvas.drawText(displayCount, textX, baselineY, numPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fontMetrics = numPaint.fontMetrics

        //  max possible text width
        val maxTextWidth = numPaint.measureText(MAX_COUNT_STRING)
        //  estimate max possible text height
        val maxTextHeight = fontMetrics.bottom - fontMetrics.top
        //  add padding to width
        val desiredWidth = (maxTextWidth + paddingLeft + paddingRight).roundToInt()
        //  add padding to height
        val desiredHeight = (maxTextHeight * 2f + paddingTop + paddingBottom).roundToInt()

        //  reconcile
        val measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec)
        val measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec)

        //  store the final measured dimensions
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun reconcileSize(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec) //  said by parent
        val specSize = MeasureSpec.getSize(measureSpec) //  said by parent

        return when (mode) {
            MeasureSpec.UNSPECIFIED ->
                contentSize

            MeasureSpec.AT_MOST -> if (contentSize < specSize)
                contentSize
            else
                specSize

            MeasureSpec.EXACTLY ->
                specSize

            else ->
                specSize
        }
    }
}

private interface ITallyCounter {
    fun reset()
    fun increment()
    fun getCount(): Int
    fun setCount(count: Int)
}