package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: RegisterResultResponse? = null
) {
    data class RegisterResultResponse(
        @SerializedName("email")
        val email: String? = null,

        @SerializedName("noHp")
        val noHp: String? = null,

        @SerializedName("password")
        val password: String? = null,

        @SerializedName("fullName")
        val fullName: String? = null,

        @SerializedName("birthDate")
        val birthDate: String? = null,

        @SerializedName("gender")
        val gender: String? = null
    )
}
