package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * if using form data
 * */
data class RegisterRequest(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("noHp")
    val noHp: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("fullName")
    val fullName: String? = null,

    @SerializedName("birthDate")
    val birthDate: String? = null,

    @SerializedName("gender")
    val gender: String? = null
) {
    /**
     * if using form data + multipart body
     * */
    fun toPartMap(): HashMap<String, RequestBody> {
        return hashMapOf(
            "email" to email.toString().toRequestBody(MultipartBody.FORM),
            "noHp" to noHp.toString().toRequestBody(MultipartBody.FORM),
            "password" to password.toString().toRequestBody(MultipartBody.FORM),
            "fullName" to fullName.toString().toRequestBody(MultipartBody.FORM),
            "birthDate" to birthDate.toString().toRequestBody(MultipartBody.FORM),
            "gender" to gender.toString().toRequestBody(MultipartBody.FORM)
        )
    }

    /**
     * if using form-urlencode
     * */
    fun toFieldMap(): HashMap<String, String> {
        return hashMapOf(
            "email" to email.toString(),
            "noHp" to noHp.toString(),
            "password" to password.toString(),
            "fullName" to fullName.toString(),
            "birthDate" to birthDate.toString(),
            "gender" to gender.toString()
        )
    }
}
