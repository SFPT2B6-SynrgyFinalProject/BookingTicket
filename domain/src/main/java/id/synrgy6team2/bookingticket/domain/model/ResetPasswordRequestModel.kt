package id.synrgy6team2.bookingticket.domain.model

data class ResetPasswordRequestModel(
    val newPassword: String? = null,
    val confirmPassword: String? = null
)
