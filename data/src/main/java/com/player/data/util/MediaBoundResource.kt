package com.player.data.util

import com.player.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> mediaBondResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: suspend (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow: Flow<Resource<ResultType>> = if (shouldFetch(data)) {
        try {
            val fetchResult = fetch()
            saveFetchResult(fetchResult)
            query().map { Resource.Success(it) }
        } catch (err: Throwable) {
            query().map { Resource.Error(err, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}.catch { err ->
    emit(Resource.Error(err))
}.flowOn(Dispatchers.IO)