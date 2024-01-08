package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordResponse
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel

fun ResetPasswordRequestModel.toData(): ResetPasswordRequest {
    return ResetPasswordRequest(
        this.otp, this.newPassword
    )
}

fun ResetPasswordResponse.toDomain(): ResetPasswordResponseModel {
    return ResetPasswordResponseModel(
        this.status, this.data?.toDomain()
    )
}

fun ResetPasswordResponse.ResetPasswordResultResponse.toDomain(): ResetPasswordResponseModel.ResetPasswordResultResponseModel {
    return ResetPasswordResponseModel.ResetPasswordResultResponseModel(
        this.email, this.roles, this.token
    )
}