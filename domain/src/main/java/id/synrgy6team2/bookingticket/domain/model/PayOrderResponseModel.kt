package id.synrgy6team2.bookingticket.domain.model

data class PayOrderResponseModel(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
) {
	data class Data(
		val orderId: String? = null
	)
}
