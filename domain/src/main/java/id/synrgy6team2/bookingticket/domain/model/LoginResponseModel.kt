package id.synrgy6team2.bookingticket.domain.model

data class LoginResponseModel(
    val status: String? = null,
    val data: LoginResultResponseModel? = null
) {
    data class LoginResultResponseModel(
        val fullname: String? = null,
        val role: String? = null,
        val token: String? = null
    )
}
