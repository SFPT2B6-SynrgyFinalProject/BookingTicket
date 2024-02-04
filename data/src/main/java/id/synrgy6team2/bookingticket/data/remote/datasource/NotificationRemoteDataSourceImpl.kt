package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.gson.Gson
import id.synrgy6team2.bookingticket.data.remote.model.NotificationResponse
import id.synrgy6team2.bookingticket.data.remote.service.NotificationService

class NotificationRemoteDataSourceImpl(
    private val service: NotificationService
) : NotificationRemoteDataSource {
    private data class ErrorMessage(
        val data: Data? = null,
        val message: String? = null,
        val status: String? = null
    ) {
        data class Data(
            val notification: String? = null
        )
    }

    private fun specificErrorMessage(message: ErrorMessage): String {
        return when {
            !message.data?.notification.isNullOrEmpty() -> message.data?.notification
            else -> "Terjadi kesalahan"
        } ?: "Terjadi kesalahan pada server"
    }

    override suspend fun getNotif(token: String): NotificationResponse? {
        val response = service.getNotif("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }
}