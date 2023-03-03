package com.example.vfndemo.Utils.AutoVideoPlayer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vfndemo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.*
import javax.inject.Inject


@HiltViewModel
class ExoPlayerColumnAutoplayViewModel @Inject constructor() : ViewModel() {

    val videos = MutableStateFlow<List<VideoItem>>(listOf())
    var index: Int? = null
    private val retrofit: ApiInterface? = null
    var arraylist = ArrayList<String>()
    val map: HashMap<Int, String> = HashMap()
    var maps: List<Map<String, String>> = ArrayList()
    private var _galleryAndCacheUri: MutableStateFlow<Array<String>> =
        MutableStateFlow(emptyArray())

    init {
        populateListWithFakeData()
    }

    fun downloadFileToCacheAndGallery(link: String, contentType: Media.ContentType) {

        viewModelScope.launch {
            val inputStream = downloadMedia(link)?.byteStream()
            Log.e("RUNNN", inputStream.toString())
            Log.e("RUNNN", link.toString())

            inputStream?.let {
                val mediaType =
                    if (contentType == Media.ContentType.VIDEO) Media.Type.Video else Media.Type.Image
                val format = getFormat(link)

                val cache = MainApplication.mContext.saveToCacheAndCopy(it, format)!!
                val gallery = MainApplication.mContext.copyMediaToGallery(cache, mediaType, format, true)
                Log.e("RUNNN", cache)
                Log.e("RUNNN", gallery)
                _galleryAndCacheUri.value = arrayOf(cache, gallery)
            }
        }
    }

    fun saveVideo(path: String, index: Int) {
        val fileName = videos.value[index - 1].mediaUrl.substring(
            videos.value[index - 1].mediaUrl.lastIndexOf("/") + 1
        )
        viewModelScope.launch {
            val responseBody = retrofit?.downloadFile(videos.value[0].mediaUrl)?.body()
            saveFile(responseBody, path + fileName)
            Log.e("GetFilePAth", fileName.toString())
            Log.e("GetFilePAth", path.toString())
            Log.e("GetFilePAth", index.toString())
            arraylist.add("$path/$fileName")
            arraylist.add("$path/$fileName")
            arraylist.add("$path/$fileName")
//            map[1] = "$path/$fileName"
//            map[2] = "$path/$fileName"
            Log.e("printListt", arraylist.size.toString())
            Log.e("printListt", arraylist[0].toString())
            Log.e("map", map.toString())
            Log.e("map", map[0].toString())
            Log.e("map", map[1].toString())
        }
    }

    private fun populateListWithFakeData() {
        val testVideos = listOf(
            VideoItem(
                1,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
                "Mayank"
            ),
            VideoItem(
                2,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
                "Madhur"
            ),
            VideoItem(
                3,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
                "Manjit Singh"
            ),
            VideoItem(
                4,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
                "Ram Sharma"
            ),
            VideoItem(
                5,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
                "Deepak"
            ),
            VideoItem(
                6,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
                "Ayush"
            ),
            VideoItem(
                7,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
                "Vikash"
            ),
            VideoItem(
                8,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
                "Rakesh"
            ),
            VideoItem(
                9,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDrift.jpg",
                "Shreya"
            ),
            VideoItem(
                10,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
                "Amit"
            ),
        )
        videos.value = testVideos
    }

    private fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            //val file = File(getCacheDir(), "cacheFileAppeal.srl")
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }

//    fun isOnline(): Boolean {
//        val connectivityMgr = MainActivity.getInstance().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        val network = connectivityMgr.activeNetwork
//
//        val networkCapabilities = connectivityMgr.getNetworkCapabilities(network)
//
//        return networkCapabilities?.let {
//            (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
//                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
//                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
//        } ?: run {
//            false
//        }
//    }

//    fun getNetworkInterfaceSticker(): NetworkInterface? {
//        if (networkInterfaceSticker != null) return networkInterfaceSticker
//        synchronized(this) {
//            val cacheFile = File(cacheDir, "responses")
//            networkInterfaceSticker = NetworkModule.getNetworkModuleSticker(cacheFile)
//            return networkInterfaceSticker
//        }
//    }

    private suspend fun downloadMedia(link: String): ResponseBody? {
//        if (!isOnline()) {
//            showToast(Constants.NO_INTERNET)
//            return null
//        }

        val response = retrofit?.downloadFile(link)
        Log.e("Responsee", response?.body().toString())

        return if (response?.isSuccessful == true)
            response.body() else null
    }

}

interface ApiInterface {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

}


