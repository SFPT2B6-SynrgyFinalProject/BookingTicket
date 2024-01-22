package id.synrgy6team2.bookingticket.presentation.notification

import androidx.annotation.DrawableRes

data class NotificationModel(
    @DrawableRes val img: Int,
    val description: String
)
