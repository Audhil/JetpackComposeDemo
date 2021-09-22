package com.example.xjpackcompose.presentation.ui.custom_views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

//  https://www.youtube.com/watch?v=NYtB6mlu7vA
//  sample view group
class SimpleListItem : ViewGroup {

    private var icon: ImageView? = null
    private var titleView: TextView? = null
    private var subtitleView: TextView? = null

    constructor(context: Context) : super(context)

    //  best place to add listeners, callbacks, resources
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    //  gets called when view is recycling in listview when scrolling, calling remove view, activity is tear down,
    //  best place to remove listeners, callbacks, resources, threads, scrolls etc
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var layoutParams = icon?.layoutParams as MarginLayoutParams

        // Figure out the x-coordinate and y-coordinate of the icon.
        var x = paddingLeft + layoutParams.leftMargin
        var y = paddingTop + layoutParams.topMargin

        //  layout the icon
        icon?.layout(x, y, x + (icon?.measuredWidth ?: 0), y + (icon?.measuredHeight ?: 0))

        // Calculate the x-coordinate of the title: icon's right coordinate +
        // the icon's right margin.
        x += icon?.measuredWidth ?: 0 + layoutParams.rightMargin

        // Add in the title's left margin.
        layoutParams = titleView?.layoutParams as MarginLayoutParams
        x += layoutParams.leftMargin

        // Calculate the y-coordinate of the title: this ViewGroup's top padding +
        // the title's top margin
        y = paddingTop + layoutParams.topMargin
        
        // Layout the title.
        titleView?.layout(
            x, y,
            x + (titleView?.measuredWidth ?: 0), y + (titleView?.measuredHeight ?: 0)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //  measure icon
        measureChildWithMargins(icon, widthMeasureSpec, 0, heightMeasureSpec, 0)
        //  find out how much width the icon used
        val lp = icon?.layoutParams as MarginLayoutParams
        val widthUsed = (icon?.measuredWidth ?: 0) + lp.leftMargin + lp.rightMargin
        val heightUsed = 0

        //  measure title
        measureChildWithMargins(
            titleView,
            // Pass width constraints and width already used.
            widthMeasureSpec,
            widthUsed,
            // Pass height constraints and height already used.
            heightMeasureSpec,
            heightUsed
        )

        //  measure sub title
        measureChildWithMargins(
            subtitleView,
            // Pass width constraints and width already used.
            widthMeasureSpec, widthUsed,
            // Pass height constraints and height already used.
            heightMeasureSpec, heightUsed
        )
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return generateDefaultLayoutParams()
    }

    //  save & restore states
    override fun onSaveInstanceState(): Parcelable? {
        println("yup: onSaveInstanceState custom view")
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        super.onRestoreInstanceState(state)
        println("yup: onRestoreInstanceState custom view")
    }
}