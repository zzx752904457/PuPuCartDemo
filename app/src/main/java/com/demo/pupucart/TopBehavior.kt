package com.demo.pupucart

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.abs

/**
 * @Package com.demo.pupucart
 * @ClassName TopBehavior
 * @description 上方列表的behavior
 * @author Zeng ZhaoXuan
 * @time 2021/6/8 2:22 下午
 */
class TopBehavior : CoordinatorLayout.Behavior<RecyclerView> {

    // 界面整体向上滑动，达到列表可滑动的临界点
    private var upReach = false

    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private var downReach = false

    // 列表上一个全部可见的item位置
    private var lastPosition = -1

    constructor()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: RecyclerView,
        ev: MotionEvent
    ): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: RecyclerView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        val recyclerView = coordinatorLayout.findViewById<RecyclerView>(R.id.recycler_two)
        val homeSearchBarMaxTranslationY = child.height
        // 列表第一个全部可见Item的位置
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val pos: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (pos == 0 && pos < lastPosition) {
            downReach = !(dy < 0 && child.translationY.toInt() == -homeSearchBarMaxTranslationY && !upReach)
        }
        lastPosition = pos
        // 整体可以滑动，否则下方RecyclerView消费滑动事件
        if (canScroll(child, dy.toFloat(), homeSearchBarMaxTranslationY)) {
            if ((dy < 0 && pos == 0) || dy >= 0) {
                var finalY = child.translationY - dy
                if (finalY < -homeSearchBarMaxTranslationY) {
                    finalY = -homeSearchBarMaxTranslationY.toFloat()
                    upReach = true
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.translationY = finalY
                // 告知父布局CoordinatorLayout，该事件已被上方RecyclerView消费掉了
                if (abs(child.translationY) < homeSearchBarMaxTranslationY) {
                    if (child.translationY == 0f) {
                        if (dy > 0) {
                            consumed[1] = dy
                            return
                        }
                    } else {
                        consumed[1] = dy
                        return
                    }
                }
            }
        }
        upReach = false
    }

    private fun canScroll(child: View, scrollY: Float, maxTranslationY: Int): Boolean {
        if (scrollY > 0 && child.translationY.toInt() == -maxTranslationY && !upReach) {
            return false
        }
        return !downReach
    }
}
