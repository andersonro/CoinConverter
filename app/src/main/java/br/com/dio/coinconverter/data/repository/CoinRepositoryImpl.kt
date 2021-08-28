package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.core.exceptions.RemoteException
import br.com.dio.coinconverter.data.database.AppDatabase
import br.com.dio.coinconverter.data.database.dao.ExchangeDao
import br.com.dio.coinconverter.data.model.ExchangesResponseValue
import br.com.dio.coinconverter.data.model.ResponseError
import br.com.dio.coinconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImpl(private val service: AwesomeService, private val appDatabase: AppDatabase): CoinRepository {

    private val dao = appDatabase.exchangeDao()

    override suspend fun getExchangeValue(coins: String) = flow {

        try {
            val exchageValue = service.exchangeValues(coins)
            val exchange = exchageValue.values.first()
            emit(exchange)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, ResponseError::class.java)
            throw RemoteException(error.message)
        }


    }

    override suspend fun save(exchangesResponseValue: ExchangesResponseValue) {
        dao.insert(exchangesResponseValue)
    }

    override fun list(): Flow<List<ExchangesResponseValue>> {
        return dao.findAll()
    }
}