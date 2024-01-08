package id.synrgy6team2.bookingticket.domain.model

data class ResetPasswordResponseModel(
    val status: String? = null,
    val data: ResetPasswordResultResponseModel? = null
) {
    data class ResetPasswordResultResponseModel(
        val email: String? = null,
        val roles: List<String?>? = null,
        val token: String? = null
    )
}