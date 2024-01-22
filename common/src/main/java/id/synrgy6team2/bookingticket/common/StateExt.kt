package id.synrgy6team2.bookingticket.common

sealed class State<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Success<T>(data: T) : State<T>(data = data)
    class Error<T>(code: Int?, message: String) : State<T>(code = code, message = message)
    class Loading<T> : State<T>()
}

sealed class StateLocal<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : StateLocal<T>(data)
    class Loading<T>(data: T? = null) : StateLocal<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : StateLocal<T>(data, throwable)
}