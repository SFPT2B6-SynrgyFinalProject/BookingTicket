package id.synrgy6team2.bookingticket.domain.model

data class ProfileResponseModel(
    val status: String? = null,
    val data: ProfileResultResponseModel? = null
) {
    data class ProfileResultResponseModel(
        val email: String? = null,
        val fullName: String? = null,
        val gender: String? = null,
        val birthDate: String? = null,
        val noHp: String? = null
    )
}
