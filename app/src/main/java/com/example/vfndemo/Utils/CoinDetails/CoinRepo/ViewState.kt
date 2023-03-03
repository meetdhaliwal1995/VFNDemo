package com.example.vfndemo.Utils.CoinDetails.CoinRepo

import androidx.annotation.Keep

sealed class ViewState<out T> {
     @Keep
     data class ContentLoaded<out T>(val value: T) : ViewState<T>()
     object Loading : ViewState<Nothing>()
     @Keep
     data class LoadFailed(val errorType:Int = -1,val errorResponse:String? = null): ViewState<Nothing>()
}