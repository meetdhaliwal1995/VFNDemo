package com.example.vfndemo.Utils.CoinDetails

import androidx.annotation.Keep

@Keep
data class ResponseDataWrapper<T>(val data: T? = null, val message: String? = null)
