package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("otp")
    val otp: String? = null,

    @SerializedName("newPassword")
    val newPassword: String? = null
)
