package id.synrgy6team2.bookingticket.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<StateLocal<ResultType>> = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(StateLocal.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { StateLocal.Success(it) }
        } catch (throwable: Throwable) {
            query().map { StateLocal.Error(throwable, it) }
        }
    } else {
        query().map { StateLocal.Success(it) }
    }
    emitAll(flow)
}