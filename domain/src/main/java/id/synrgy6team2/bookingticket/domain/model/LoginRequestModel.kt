package id.synrgy6team2.bookingticket.domain.model

data class LoginRequestModel(
    val email: String? = null,
    val password: String? = null,
    val googleToken: String? = null
)