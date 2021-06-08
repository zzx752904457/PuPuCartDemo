package com.demo.pupucart

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * @Package com.demo.pupucart
 * @ClassName BottomBehavior
 * @description 下方列表的behavior
 * @author Zeng ZhaoXuan
 * @time 2021/6/8 2:22 下午
 */
class BottomBehavior : CoordinatorLayout.Behavior<RecyclerView> {
    constructor() {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        child.y = dependency.height + dependency.translationY
        return true
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: RecyclerView,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        var availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
        if (availableHeight == 0) {
            availableHeight = parent.height
        }

        val height = availableHeight
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        parent.onMeasureChild(
            child,
            parentWidthMeasureSpec,
            widthUsed,
            heightMeasureSpec,
            heightUsed
        )
        return true
    }
}