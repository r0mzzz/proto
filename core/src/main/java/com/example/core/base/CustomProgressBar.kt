package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

internal class CustomProgressBar : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setStyle(STYLE_NO_FRAME, com.example.uikit.R.style.Theme_Proto)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(com.example.uikit.R.layout.loading_dialog, container, false)
    }

    fun show(fragmentManager: FragmentManager) {
        if (!isAdded) {
            super.show(fragmentManager, TAG)
        }
    }

    fun hide() {
        if (isAdded) {
            dismissAllowingStateLoss()  // Dismiss and avoid state loss
        }
    }

    companion object {
        private const val TAG = "com.example.core.base.CustomProgressBar"

        // Factory method to create a new instance of the progress bar dialog
        fun newInstance(): CustomProgressBar {
            return CustomProgressBar()
        }
    }
}