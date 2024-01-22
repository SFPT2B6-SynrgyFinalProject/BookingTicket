package id.synrgy6team2.bookingticket.domain.model

data class ChangePasswordRequestModel(
    val currentPassword: String? = null,
    val newPassword: String? = null,
    val confirmPassword: String? = null
)
