package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getExchangeValue(coins: String) : Flow<ExchangesResponseValue>

    suspend fun save(exchangesResponseValue: ExchangesResponseValue)
    fun list(): Flow<List<ExchangesResponseValue>>
}