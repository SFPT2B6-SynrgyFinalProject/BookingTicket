package id.synrgy6team2.bookingticket.domain.model

data class RegisterResponseModel(
    val status: String? = null,
    val data: RegisterResultResponse? = null
) {
    data class RegisterResultResponse(
        val email: String? = null,
        val fullName: String? = null,
        val birthDate: String? = null,
        val gender: String? = null
    )
}
