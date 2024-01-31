package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class PayOrderRequest(

	@field:SerializedName("cvv")
	val cvv: String? = null,

	@field:SerializedName("cardName")
	val cardName: String? = null,

	@field:SerializedName("orderId")
	val orderId: String? = null,

	@field:SerializedName("expiredDate")
	val expiredDate: String? = null,

	@field:SerializedName("cardNumber")
	val cardNumber: String? = null
)
