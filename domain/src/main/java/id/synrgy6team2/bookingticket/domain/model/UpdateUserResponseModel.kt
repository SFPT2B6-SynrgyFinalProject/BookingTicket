package id.synrgy6team2.bookingticket.domain.model

data class UpdateUserResponseModel(
    val status: String? = null,
    val data: UpdateUserResultResponseModel? = null
) {
    data class UpdateUserResultResponseModel(
        val email: String? = null,
        val fullName: String? = null,
        val noHp: String? = null,
        val birthDate: String? = null,
        val gender: String? = null
    )
}
