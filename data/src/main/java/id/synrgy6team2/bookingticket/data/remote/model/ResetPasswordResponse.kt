package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel

data class ResetPasswordResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: ResetPasswordResultResponse? = null
) {
    data class ResetPasswordResultResponse(
        @SerializedName("fullname")
        val email: String? = null,

        @SerializedName("roles")
        val roles: List<String?>? = null,

        @SerializedName("token")
        val token: String? = null
    )
}