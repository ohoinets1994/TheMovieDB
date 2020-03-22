package com.example.themoviewdb.extensions

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.themoviewdb.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer { observer(it) })
}

fun Throwable.getErrorMessage(context: Context): String =
    context.getString(
        when (this) {
            is UnknownHostException -> R.string.error_no_internet_connection
            is HttpException -> when (code()) {
                in 400..499 -> R.string.error_status_code_400
                in 500..599 -> R.string.error_status_code_500
                else -> R.string.error_something_went_wrong
            }
            else -> R.string.error_something_went_wrong
        }
    )

inline fun CoroutineScope.load(
    error: MutableLiveData<Throwable>,
    crossinline action: suspend () -> Unit
) = launch {
    try {
        action()
    } catch (e: Exception) {
        error.postValue(e)
    }
}