package id.synrgy6team2.bookingticket.domain.model

data class CreateOrderResponseModel(
    val data: Data? = null
) {
    data class Data(
        val orderId: String? = null,
        val status: String? = null
    )
}
