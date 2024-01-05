package id.synrgy6team2.bookingticket.domain.model

data class RegisterRequestModel(
    val email: String? = null,
    val noHp: String? = null,
    val password: String? = null,
    val fullName: String? = null,
    val birthDate: String? = null,
    val gender: String? = null
)
