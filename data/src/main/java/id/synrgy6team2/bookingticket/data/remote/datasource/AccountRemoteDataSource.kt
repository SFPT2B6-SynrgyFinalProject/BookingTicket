package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.ProfileResponse
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserRequest
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserResponse

interface AccountRemoteDataSource {
    suspend fun profile(token: String): ProfileResponse?
    suspend fun updateUser(token: String, field: UpdateUserRequest): UpdateUserResponse?
    suspend fun changePassword(token: String, field: ChangePasswordRequest): ChangePasswordResponse?
}