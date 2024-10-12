package com.devmichael.instagramredesign.helpers

import android.os.SystemClock
import android.view.View
import android.view.ViewConfiguration

class DoubleClickListener(private val onSingleClick: () -> Unit, private val onDoubleClick: () -> Unit) : View.OnClickListener {
    private var lastClickTime: Long = -1

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.elapsedRealtime()
        val timeDifference = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        if (timeDifference < ViewConfiguration.getDoubleTapTimeout()) {
            onDoubleClick()
        } else {
            onSingleClick()
        }
    }
}