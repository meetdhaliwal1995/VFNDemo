package com.example.vfndemo.Utils.CoinDetails.Model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
@Keep
data class CoinInfoItem(
    val currentPrice: BigDecimal?,
    val coinId: String?,
    val imageUrl: String?,
    val marketCap: BigDecimal?,
    val firstName: String?,
    val priceChangePercentage7D: Double?,
    val userName: String?,
    val fullyDilutedValuation: BigDecimal?,
    val totalVolume: BigDecimal?,
    val totalSupply: BigDecimal?,
    val circulatingSupply: BigDecimal?,
    val priceChangePercentage24H: Double?

):Parcelable

@Parcelize
@Keep
data class CoinInfoModel(
    val currentPrice: BigDecimal,
    val id: String?,
    val imageUrl: String?,
    val marketCap: BigDecimal?,
    val name: String?,
    val priceChangePercentage7D: BigDecimal?,
    val symbol: String?,
    val marketCapRank: Int?,
    val priceChangePercentage24H: BigDecimal?,
    val lastUpdated: String?,
    val allTimeHighChangePercentage: BigDecimal?,
    val allTimeLowChangePercentage: BigDecimal?,
    val priceChangePercentage14D: BigDecimal?,
    val priceChangePercentage1Y: BigDecimal?,
    val priceChangePercentage200D: BigDecimal?,
    val priceChangePercentage30D: BigDecimal?,
    val sahifolioTradeable: Boolean?,
    val exchangeTradeable: Boolean?,
    val totalSupply: BigDecimal?,
    val totalVolume: BigDecimal?,
    val fullyDilutedValuation: BigDecimal?,
    val circulatingSupply: BigDecimal?,

):Parcelable