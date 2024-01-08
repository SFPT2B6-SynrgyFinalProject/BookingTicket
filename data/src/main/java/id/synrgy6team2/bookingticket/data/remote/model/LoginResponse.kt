package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: LoginResultResponse? = null
) {
    data class LoginResultResponse(
        @SerializedName("fullname")
        val email: String? = null,

        @SerializedName("roles")
        val roles: List<String?>? = null,

        @SerializedName("token")
        val token: String? = null
    )
}
