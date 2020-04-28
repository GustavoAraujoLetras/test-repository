package com.example.test.myapplication

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.getDrawableOrThrow

class CustomView : FrameLayout {

    private var textView: AppCompatTextView
    private var imageView: AppCompatImageView

    var text: CharSequence?
        get() = textView.text
        set(value) { textView.text = value }

    constructor(context: Context) : super(context) {
        applyAttributeSet(attrs = null, defStyleAttr = 0, defStyleRes = 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyAttributeSet(attrs, defStyleAttr = 0, defStyleRes = 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyAttributeSet(attrs, defStyleAttr, defStyleRes = 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        applyAttributeSet(attrs, defStyleAttr, defStyleRes)
    }

    init {
        View.inflate(context, R.layout.view_custom, this)

        textView = findViewById(R.id.text_view)
        imageView = findViewById(R.id.image_view)
    }

    private fun applyAttributeSet(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomView,
            defStyleAttr,
            defStyleRes
        )

        try {
            textView.text = typedArray.getString(typedArray.getIndex(R.styleable.CustomView_android_text))
            textView.background = typedArray.getDrawableOrThrow(typedArray.getIndex(R.styleable.CustomView_android_src))
        } finally {
            typedArray.recycle()
        }
    }
}