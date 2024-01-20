package id.synrgy6team2.bookingticket.presentation.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileModel(
    @DrawableRes val img: Int,
    @StringRes val title: Int,
    val id: String
)
