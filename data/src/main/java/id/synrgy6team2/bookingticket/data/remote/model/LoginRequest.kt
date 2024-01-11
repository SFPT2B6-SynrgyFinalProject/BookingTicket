package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * if using form data
 * */
data class LoginRequest(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("googleToken")
    val googleToken: String? = null
) {
    /**
     * if using form data + multipart body
     * */
    fun toPartMap(): HashMap<String, RequestBody> {
        return hashMapOf(
            "email" to email.toString().toRequestBody(MultipartBody.FORM),
            "password" to password.toString().toRequestBody(MultipartBody.FORM),
            "googleToken" to googleToken.toString().toRequestBody(MultipartBody.FORM)
        )
    }

    /**
     * if using form-urlencode
     * */
    fun toFieldMap(): HashMap<String, String> {
        return hashMapOf(
            "email" to email.toString(),
            "password" to password.toString(),
            "googleToken" to googleToken.toString()
        )
    }
}