package id.synrgy6team2.bookingticket.common

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun Activity.onToastSuccess(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}

fun Activity.onToastInfo(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}

fun Activity.onToastWarning(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}

fun Activity.onToastDelete(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.DELETE,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}

fun Activity.onToastError(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}

fun Activity.onToastNoInternet(title: String?, message: String?) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        MotionToastStyle.NO_INTERNET,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}