package id.synrgy6team2.bookingticket.data.remote.datasource

interface AuthenticationDataSource {
    suspend fun login()
}