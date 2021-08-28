package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(private val repository: CoinRepository) : UseCase<String, ExchangesResponseValue>() {

    override suspend fun execute(param: String): Flow<ExchangesResponseValue> {
        return repository.getExchangeValue(param)
    }
}