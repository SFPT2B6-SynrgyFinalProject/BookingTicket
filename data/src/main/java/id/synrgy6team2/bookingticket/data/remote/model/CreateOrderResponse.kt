package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateOrderResponse(
    @SerializedName("data")
    val data: Data? = null
) {
    data class Data(
        @SerializedName("orderId")
        val orderId: String? = null,

        @SerializedName("status")
        val status: String? = null
    )
}
