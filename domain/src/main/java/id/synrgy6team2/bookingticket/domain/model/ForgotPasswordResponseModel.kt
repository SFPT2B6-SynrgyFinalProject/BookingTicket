package id.synrgy6team2.bookingticket.domain.model

data class ForgotPasswordResponseModel(
    val status: String? = null,
    val data: ForgotPasswordResultResponseModel? = null
) {
    data class ForgotPasswordResultResponseModel(
        val email: String? = null,
        val message: String? = null
    )
}
