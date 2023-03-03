package com.example.vfndemo.Utils.CoinDetails.Model


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class GraphInfoModel(
    @SerializedName("data")
    val data: DataGraph,
    @SerializedName("message")
    val message: String
)

data class DataGraph(
    @SerializedName("prices")
    val prices: List<BigDecimal>,
    @SerializedName("timestamps")
    val timestamps: List<BigDecimal>
)