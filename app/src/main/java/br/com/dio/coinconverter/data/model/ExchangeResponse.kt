package br.com.dio.coinconverter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.dio.coinconverter.core.extensions.DateTimeCurrent
import java.util.*
import kotlin.collections.HashMap

typealias ExchangesResponse = HashMap<String, ExchangesResponseValue>

@Entity(tableName = "tb_exchange")
data class ExchangesResponseValue (

    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val code: String = "",
    val codein: String = "",
    val name: String = "",
    val high: String = "",
    val low: String = "",
    val varBid: String = "",
    val pctChange: String = "",
    val bid: Double,
    val ask: String = "",
    val create_date: String = "",
    val timestamp: String = "",
    val create_convert: String = "",
    val value : Double
)
