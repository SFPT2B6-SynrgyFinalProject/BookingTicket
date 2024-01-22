package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("data")
    val data: ProfileResultResponse? = null
) {
    data class ProfileResultResponse(
        @SerializedName("email")
        val email: String? = null,

        @SerializedName("fullName")
        val fullName: String? = null,

        @SerializedName("gender")
        val gender: String? = null,

        @SerializedName("birthDate")
        val birthDate: String? = null,

        @SerializedName("noHp")
        val noHp: String? = null
    )
}
