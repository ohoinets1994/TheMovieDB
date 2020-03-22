package com.example.themoviewdb.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.themoviewdb.R
import com.example.themoviewdb.extensions.getErrorMessage
import kotlinx.android.synthetic.main.layout_error_message.view.*

class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    var onRetryClicked: (() -> Unit)? = null
        set(value) = btnErrorRetry.setOnClickListener { value?.invoke() }

    private var shouldShowError = false

    var error: String?
        set(value) {
            tvErrorMessage.text = value
            shouldShowError = true
            showError()
        }
        get() = tvErrorMessage.text.toString()

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_error_message, this, true)
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0)
        error = ta.getString(R.styleable.ErrorView_errorString)
        hideError()
        ta.recycle()
    }

    fun setError(exception: Throwable) {
        error = exception.getErrorMessage(context)
    }

    fun showError() {
        shouldShowError = true
        isVisible = true
        tvErrorMessage.isVisible = true
        btnErrorRetry.isVisible = true
    }

    fun hideError() {
        shouldShowError = false
        tvErrorMessage.isVisible = false
        btnErrorRetry.isVisible = false
    }
}