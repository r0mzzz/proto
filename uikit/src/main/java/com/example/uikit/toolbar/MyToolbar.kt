package com.example.uikit.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.material.icons.Icons
import com.example.uikit.R
import com.example.uikit.databinding.MyToolbarBinding
import com.example.uikit.toolbar.extensions.gone
import com.example.uikit.toolbar.extensions.show

enum class ToolbarOption(val value: Int) {
    WITH_LEFT_AND_RIGHT(0), WITH_LEFT(1), WITH_RIGHT(2), WITHOUT_LEFT_AND_RIGHT(3)
}

class MyToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs) {
    val appIcons = Icons.Rounded
    private var toolbarLeftActionClick: (() -> Unit)? = null
    private var toolbarRightActionClick: (() -> Unit)? = null
    private var toolbarOption: Int = ToolbarOption.WITH_LEFT.value
    private var title: String = ""

    private var binding: MyToolbarBinding =
        MyToolbarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        if (!isInEditMode) {
            tag = "toolbar"
            context.obtainStyledAttributes(
                attrs,
                R.styleable.MyToolbar,
                defStyleAttr,
                R.style.MyToolbarStyle
            ).apply {
                try {
                    toolbarOption = getInteger(
                        R.styleable.MyToolbar_toolbar_option,
                        ToolbarOption.WITH_LEFT_AND_RIGHT.value
                    )
                } finally {
                    recycle()
                }
            }
            setToolbarOptions()
            with(binding) {
                rightActionBtn.setOnClickListener {
                    toolbarRightActionClick?.invoke()
                }
                backBtn.setOnClickListener {
                    toolbarLeftActionClick?.invoke()
                }
            }
        }
    }

    private fun setToolbarOptions() {
        when (toolbarOption) {
            ToolbarOption.WITH_LEFT.value -> {
                binding.backBtn.show()
            }

            ToolbarOption.WITH_RIGHT.value -> {
                binding.rightActionBtn.show()
            }

            ToolbarOption.WITH_LEFT_AND_RIGHT.value -> {
                binding.backBtn.show()
                binding.rightActionBtn.show()

            }

            ToolbarOption.WITHOUT_LEFT_AND_RIGHT.value -> {
                binding.backBtn.gone()
                binding.rightActionBtn.gone()
            }

            else -> {
                binding.backBtn.show()
                binding.rightActionBtn.gone()
            }
        }
    }

    fun setToolBarLeftActionClick(actionClick: () -> Unit) {
        toolbarLeftActionClick = actionClick
    }

    fun setToolBarRightActionClick(actionClick: () -> Unit) {
        toolbarRightActionClick = actionClick
    }

    fun setTitle(title: String) {
        this.title = title
        binding.title.text = this.title
    }
}