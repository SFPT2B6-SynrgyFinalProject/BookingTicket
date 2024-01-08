package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordResponse
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel

fun ForgotPasswordRequestModel.toData(): ForgotPasswordRequest {
    return ForgotPasswordRequest(
        this.email
    )
}

fun ForgotPasswordResponse.toDomain(): ForgotPasswordResponseModel {
    return ForgotPasswordResponseModel(
        this.status, this.data?.toDomain()
    )
}

fun ForgotPasswordResponse.ForgotPasswordResultResponse.toDomain(): ForgotPasswordResponseModel.ForgotPasswordResultResponseModel {
    return ForgotPasswordResponseModel.ForgotPasswordResultResponseModel(
        this.email
    )
}