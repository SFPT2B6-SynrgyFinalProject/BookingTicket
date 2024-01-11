package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.LoginRequest
import id.synrgy6team2.bookingticket.data.remote.model.LoginResponse
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel

fun LoginRequestModel.toData(): LoginRequest {
    return LoginRequest(
        this.email, this.password, this.googleToken
    )
}

fun LoginResponse.toDomain(): LoginResponseModel {
    return LoginResponseModel(
        this.status, this.data?.toDomain()
    )
}

fun LoginResponse.LoginResultResponse.toDomain(): LoginResponseModel.LoginResultResponseModel {
    return LoginResponseModel.LoginResultResponseModel(
        this.email, this.roles, this.token
    )
}