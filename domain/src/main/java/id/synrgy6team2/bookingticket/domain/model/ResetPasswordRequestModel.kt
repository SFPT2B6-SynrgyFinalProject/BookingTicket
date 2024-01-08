package id.synrgy6team2.bookingticket.domain.model

data class ResetPasswordRequestModel(
    val otp: String? = null,
    val newPassword: String? = null
)
