//package com.example.vfndemo.Utils.CoinDetails.CoinRepo
//
//import androidx.annotation.Keep
//import androidx.constraintlayout.motion.utils.ViewState
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
//import com.example.vfndemo.Utils.CoinDetails.GraphModel
//import com.example.vfndemo.Utils.CoinDetails.Model.UserInfoModel
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.flow.onStart
//import javax.inject.Inject
//
//
//class CoinViewModel @Inject constructor(
//    val coinRepository: CoinRepository,
//) : ViewModel() {
//    public var userSearchResponse: UserSearchResponseModel? = null
//
//    fun getCoinChart(coinId: String, interval: String): LiveData<com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState<GraphModel>> {
//        return liveData {
//            coinRepository.getCoinChart(coinId, interval).onStart { emit(com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.Loading) }
//                .collectLatest {
//                    when (it) {
//                        is ResultWrapper.NetworkError,
//                        is ResultWrapper.GenericError,
//                        -> {
////                            emit(com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.LoadFailed)
//                        }
//                        is ResultWrapper.Success<GraphModel> -> {
//                            if (it.value.prices?.isNotEmpty() == true)
//                                emit(com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.ContentLoaded(it.value))
//                            else
//                                emit(com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.LoadFailed())
//                        }
//                    }
//                }
//        }
//    }
//
//}
//
//@Keep
//data class UserSearchResponseModel(
//    val message: String,
//    val data: List<UserInfoModel>
//)
//
