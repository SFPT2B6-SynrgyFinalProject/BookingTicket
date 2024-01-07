package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: Any? = null
)
