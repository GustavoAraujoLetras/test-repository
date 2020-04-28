package com.example.test.myapplication

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.CompoundButton
import androidx.core.widget.TextViewCompat
import com.google.android.material.chip.Chip

class LetrasChip : Chip {

    interface OnSelectedChangedListener {
        fun onSelectedChanged(compoundButton: CompoundButton, isSelected: Boolean)
    }

    private var defaultTextEndPadding: Float = 0f

    private var textAppearanceRes: Int? = null
    private var checkedTextAppearanceRes: Int? = null
    private var onSelectedChangeListener: (CompoundButton, Boolean) -> Unit = { _, _ -> }

    constructor(context: Context) : super(context) {
        init(attrs = null, defStyleAttr = 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, defStyleAttr = 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if (isSelected && checkedTextAppearanceRes != null) {
            TextViewCompat.setTextAppearance(this, checkedTextAppearanceRes!!)

        } else if (!isSelected && textAppearanceRes != null) {
            TextViewCompat.setTextAppearance(this, textAppearanceRes!!)
        }

        onSelectedChangeListener(this, isSelected)
    }

    private fun applyAttributeSet(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetrasChip, defStyleAttr, 0)

        var textAppearanceRes: Int? = null
        var checkedTextAppearanceRes: Int? = null

        try {
            textAppearanceRes = R.styleable.LetrasChip_chip_textAppearance
                .takeIf { typedArray.hasValue(it) }
                ?.let { typedArray.getResourceId(it, R.style.TextAppearance_Letras_Chip) }

            checkedTextAppearanceRes = R.styleable.LetrasChip_chip_textAppearance_checked
                .takeIf { typedArray.hasValue(it) }
                ?.let { typedArray.getResourceId(it, R.style.TextAppearance_Chip_Checked) }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        } finally {
            typedArray.recycle()
        }

        if (textAppearanceRes != null) {
            this.textAppearanceRes = textAppearanceRes
        }

        this.checkedTextAppearanceRes = checkedTextAppearanceRes ?: this.textAppearanceRes
    }

    private fun applyAndroidAttributeSet(attrs: AttributeSet, defStyleAttr: Int) {
        val androidAttributes = arrayOf(android.R.attr.textAppearance).toIntArray()
        val typedArray = context.obtainStyledAttributes(attrs, androidAttributes, defStyleAttr, 0)

        var textAppearanceRes: Int? = null

        try {
            textAppearanceRes = androidAttributes.indexOf(android.R.attr.textAppearance)
                .let { typedArray.getResourceId(it, R.style.TextAppearance_Letras_Chip) }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        } finally {
            typedArray.recycle()
        }

        this.textAppearanceRes = textAppearanceRes
        checkedTextAppearanceRes = textAppearanceRes
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            applyAndroidAttributeSet(attrs, defStyleAttr)
            applyAttributeSet(attrs, defStyleAttr)
        }

        defaultTextEndPadding = textEndPadding
    }

    fun setOnSelectedChangeListener(listener: OnSelectedChangedListener) {
        onSelectedChangeListener = listener::onSelectedChanged
    }

    fun setOnSelectedChangeListener(listener: (CompoundButton, Boolean) -> Unit) {
        onSelectedChangeListener = listener
    }

    companion object {
        private val TAG = LetrasChip::class.java.simpleName
    }

}