package com.example.uikit.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.material.icons.Icons
import com.example.uikit.R
import com.example.uikit.databinding.MyToolbarBinding
import com.example.uikit.extensions.gone
import com.example.uikit.extensions.show

enum class ToolbarOption(val value: Int) {
    WITH_LEFT_AND_RIGHT(0), WITH_LEFT(1), WITH_RIGHT(2), WITHOUT_LEFT_AND_RIGHT(3), WITH_RIGHT_AND_SECONDARY(
        4
    )
}

enum class LogoOption(val value: Int) {
    YES(1), NO(0)
}

class MyToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs) {
    private var toolbarLeftActionClick: (() -> Unit)? = null
    private var toolbarRightActionClick: (() -> Unit)? = null
    private var toolbarSecondaryRightActionClick: (() -> Unit)? = null
    private var toolbarOption: Int = ToolbarOption.WITH_LEFT.value
    private var logoOption: Int = LogoOption.YES.value
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
                    logoOption = getInteger(
                        R.styleable.MyToolbar_logo_option,
                        LogoOption.YES.value
                    )
                } finally {
                    recycle()
                }
            }
            setToolbarOptions()
            setLogoOption()
            with(binding) {
                rightActionBtn.setOnClickListener {
                    toolbarRightActionClick?.invoke()
                }
                rightActionBtn.setOnClickListener {
                    toolbarSecondaryRightActionClick?.invoke()
                }
                backBtn.setOnClickListener {
                    toolbarLeftActionClick?.invoke()
                }
            }
        }
    }

    fun setToolbarRightIcon(resId: Int) {
        binding.rightActionBtn.setImageResource(resId)
    }

    fun setToolbarSecondaryRightIcon(resId: Int) {
        binding.rightSecondaryActionBtn.setImageResource(resId)
    }

    private fun setLogoOption() {
        when (logoOption) {
            LogoOption.YES.value -> {
                binding.toolbarLogo.show()
            }

            LogoOption.NO.value -> {
                binding.toolbarLogo.gone()
            }

            else -> {
                binding.toolbarLogo.show()
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
            ToolbarOption.WITH_RIGHT_AND_SECONDARY.value -> {
                binding.rightSecondaryActionBtn.show()
                binding.rightActionBtn.show()
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

    fun setToolBarSecondaryRightAction(actionClick: () -> Unit) {
        toolbarSecondaryRightActionClick = actionClick
    }

    fun setTitle(title: String) {
        this.title = title
        binding.title.text = this.title
    }
}