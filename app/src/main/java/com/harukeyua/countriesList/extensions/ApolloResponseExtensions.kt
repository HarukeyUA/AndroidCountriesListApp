package com.harukeyua.countriesList.extensions

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import java.io.IOException

fun <T : Operation.Data> ApolloResponse<T>.throwIfError(): ApolloResponse<T>  {
    if (hasErrors()) {
        throw IOException(errors?.firstOrNull()?.message)
    }
    return this
}