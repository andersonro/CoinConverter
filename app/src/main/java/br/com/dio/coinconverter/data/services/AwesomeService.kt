package br.com.dio.coinconverter.data.services

import br.com.dio.coinconverter.data.model.ExchangesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {

    @GET("/json/last/{coins}")
    suspend fun exchangeValues(@Path("coins") coins: String) : ExchangesResponse
}