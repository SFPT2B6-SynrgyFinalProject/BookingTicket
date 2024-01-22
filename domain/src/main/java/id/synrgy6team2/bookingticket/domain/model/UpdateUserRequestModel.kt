package id.synrgy6team2.bookingticket.domain.model

data class UpdateUserRequestModel(
    val email: String? = null,
    val fullName: String? = null,
    val noHp: String? = null,
    val birthDate: String? = null,
    val gender: String? = null
)
