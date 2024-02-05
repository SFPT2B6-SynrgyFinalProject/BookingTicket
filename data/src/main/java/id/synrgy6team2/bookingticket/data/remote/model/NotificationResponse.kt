package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	data class Data(

		@field:SerializedName("notification")
		val notification: List<NotificationItem>? = null
	) {
		data class NotificationItem(

			@field:SerializedName("imageUrl")
			val imageUrl: String? = null,

			@field:SerializedName("createdDatetime")
			val createdDatetime: String? = null,

			@field:SerializedName("id")
			val id: Int? = null,

			@field:SerializedName("title")
			val title: String? = null,

			@field:SerializedName("content")
			val content: String? = null
		)
	}
}
