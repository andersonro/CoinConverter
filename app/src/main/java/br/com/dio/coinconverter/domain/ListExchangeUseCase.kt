package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(private val repository: CoinRepository): UseCase.NoParam<List<ExchangesResponseValue>>() {
    override suspend fun execute(): Flow<List<ExchangesResponseValue>> {
        return repository.list()
    }
}