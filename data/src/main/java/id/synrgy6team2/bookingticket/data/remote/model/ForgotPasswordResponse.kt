package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: ForgotPasswordResultResponse? = null
) {
    data class ForgotPasswordResultResponse(
        @SerializedName("email")
        val email: String? = null
    )
}
