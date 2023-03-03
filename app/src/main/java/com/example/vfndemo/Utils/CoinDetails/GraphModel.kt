package com.example.vfndemo.Utils.CoinDetails

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Keep
@Parcelize
data class GraphModel(
    @SerializedName("market_caps")
    val marketCaps: List<List<BigDecimal>>?,
    val prices: List<List<BigDecimal>>?,
    @SerializedName("total_volumes")
    val totalVolumes: List<List<BigDecimal>>?,
    val tradeDate: Int,
    val tradeTime: Int
) : Parcelable
