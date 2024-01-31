package id.synrgy6team2.bookingticket.domain.model

data class PayOrderRequestModel(
	val cvv: String? = null,
	val cardName: String? = null,
	val orderId: String? = null,
	val expiredDate: String? = null,
	val cardNumber: String? = null
)
