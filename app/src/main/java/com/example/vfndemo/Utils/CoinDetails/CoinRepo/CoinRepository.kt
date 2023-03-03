//package com.example.vfndemo.Utils.CoinDetails.CoinRepo
//
//import androidx.annotation.Keep
//import com.example.vfndemo.Utils.CoinDetails.GraphModel
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import retrofit2.HttpException
//import retrofit2.http.GET
//import retrofit2.http.Path
//import retrofit2.http.Query
//import java.io.IOException
//import java.lang.Exception
//import javax.inject.Inject
//
//class CoinRepository  @Inject constructor(
//    val coinApiService: CoinApiService,
//) {
//    suspend fun getCoinChart(id: String, interval: String) : Flow<ResultWrapper<GraphModel>> {
//        return NetworkHelper.safeApiCall { coinApiService.getCoinChart(id, interval) }
//    }
//}
//
//interface CoinApiService {
//    @GET("https://api.coingecko.com/api/v3/coins/{id}/market_chart")
//    suspend fun getCoinChart(@Path("id") id: String, @Query("days") days: String
//                             , @Query("vs_currency") currency : String = "usd") : GraphModel
//
//}
//
//sealed class ResultWrapper<out T> {
//    @Keep
//    data class Success<out T>(val value: T) : ResultWrapper<T>()
//    @Keep
//    data class GenericError(val code: Int? = null, val error: NetworkHelper.ErrorResponse? = null) : ResultWrapper<Nothing>()
//
//    object NetworkError : ResultWrapper<Nothing>()
//}
//
//class NetworkHelper {
//    companion object {
//        suspend fun <T> safeApiCall(
//            apiCall: suspend () -> T,
//        ): Flow<ResultWrapper<T>> {
//            return flow {
//                try {
//                    emit(ResultWrapper.Success(apiCall.invoke()))
//                } catch (throwable: Throwable) {
//                    when (throwable) {
//                        is IOException -> ResultWrapper.NetworkError
//                        is HttpException -> {
//                            val code = throwable.code()
//                            if (code != 200) {
//                                val errorResponse = convertErrorBody(throwable)
//                                emit(ResultWrapper.GenericError(code, errorResponse))
//                            } else {
//                                emit(ResultWrapper.Success(apiCall.invoke()))
//                            }
//                        }
//                        else -> {
//                            emit(ResultWrapper.GenericError())
//                        }
//                    }
//                }
//            }.flowOn(Dispatchers.IO)
//        }
//
////        suspend fun <T> safeApiCallVideo(
////            apiCall: suspend () -> T,
////        ): Flow<VideoResultWrapper<T>> {
////            return flow {
////                try {
////                    emit(VideoResultWrapper.Success(apiCall.invoke()))
////                } catch (throwable: Throwable) {
////                    when (throwable) {
////                        is IOException -> VideoResultWrapper.NetworkError
////                        is HttpException -> {
////                            val code = throwable.code()
////                            if (code != 200) {
////                                val errorResponse = convertErrorBody(throwable)
////                                emit(VideoResultWrapper.GenericError(code, errorResponse))
////                            } else {
////                                emit(VideoResultWrapper.Success(null))
////                            }
////                        }
////                        else -> {
////                            emit(VideoResultWrapper.Success(null))
////                        }
////                    }
////                }
////            }.flowOn(Dispatchers.IO)
////        }
//
//    }
//
//    class ErrorResponse {
//        var message: String? = null
//    }
//}
//
//fun convertErrorBody(throwable: HttpException): NetworkHelper.ErrorResponse? {
//
//    return try {
//        throwable.response()?.errorBody()?.let {
//            val gson = Gson()
//            val type = object : TypeToken<NetworkHelper.ErrorResponse>() {}.type
//            gson.fromJson(it.charStream(), type)
//        }
//    } catch (exception: Exception) {
//        null
//    }
//}