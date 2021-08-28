package br.com.dio.coinconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.coinconverter.data.database.dao.ExchangeDao
import br.com.dio.coinconverter.data.model.ExchangesResponseValue

@Database(entities = [ExchangesResponseValue::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDao

    companion object {
        fun getinstance(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}