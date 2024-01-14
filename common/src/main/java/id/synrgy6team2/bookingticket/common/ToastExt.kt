package id.synrgy6team2.bookingticket.common

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

enum class StyleType {
    SUCCESS, INFO, WARNING, DELETE, ERROR, NO_INTERNET
}

fun Activity.onToast(title: String?, message: String?, styleType: StyleType) {
    MotionToast.createColorToast(
        this,
        title ?: "",
        message ?: "",
        when (styleType) {
            StyleType.SUCCESS -> MotionToastStyle.SUCCESS
            StyleType.INFO -> MotionToastStyle.INFO
            StyleType.WARNING -> MotionToastStyle.WARNING
            StyleType.DELETE -> MotionToastStyle.DELETE
            StyleType.ERROR -> MotionToastStyle.ERROR
            StyleType.NO_INTERNET -> MotionToastStyle.NO_INTERNET
        },
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.main_font_family))
}