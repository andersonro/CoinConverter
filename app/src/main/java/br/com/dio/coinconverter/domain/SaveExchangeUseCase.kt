package br.com.dio.coinconverter.domain

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeUseCase(private val repository: CoinRepository): UseCase.NoSource<ExchangesResponseValue>() {
    override suspend fun execute(param: ExchangesResponseValue): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}