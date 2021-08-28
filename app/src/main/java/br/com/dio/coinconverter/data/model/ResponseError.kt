package br.com.dio.coinconverter.data.model

data class ResponseError (
    val status: Long,
    val code: String,
    val message: String
)