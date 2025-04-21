package ua.wied.domain.models

import kotlinx.coroutines.flow.Flow

typealias FlowResult<T> = Flow<Result<T>>
typealias FlowResultList<T> = Flow<Result<List<T>>>
typealias UnitFlow = Flow<Result<Unit>>