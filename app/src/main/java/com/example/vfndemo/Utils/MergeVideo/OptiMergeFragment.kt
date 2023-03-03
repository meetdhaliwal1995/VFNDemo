package com.example.vfndemo.Utils.MergeVideo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.vfndemo.*
import com.example.vfndemo.Utils.AutoVideoPlayer.ApiInterface
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.File
import java.util.ArrayList


class OptiMergeFragment : BottomSheetDialogFragment(), OptiDialogueHelper, OptiFFMpegCallback {

    private var tagName: String = OptiMergeFragment::class.java.simpleName
    private lateinit var rootView: View
    private var _galleryAndCacheUri: MutableStateFlow<Array<String>> =
        MutableStateFlow(emptyArray())
    private val retrofit: ApiInterface? = null
    private lateinit var ivClose: ImageView
    private lateinit var ivDone: ImageView
    private lateinit var ivVideoOne: TextView
    private lateinit var ivVideoTwo: TextView
    private var videoUri: Uri? = null
    private var videoFile: File? = null
    private var videoFileOne: File? = null
    private var videoFileTwo: File? = null
    private var bmThumbnailOne: Bitmap? = null
    private var bmThumbnailTwo: Bitmap? = null
    private var helper: OptiBaseCreatorDialogFragment.CallBacks? = null
    private var mContext: Context? = null
    private var pathVideoOne: String? = null
    private var pathVideoTwo: String? = null
    private lateinit var preferences: SharedPreferences
    private var permissionList: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.opti_fragment_merge_dialogg, container, false)

        preferences = activity!!.getSharedPreferences("fetch_permission", Context.MODE_PRIVATE)

        checkStoragePermission(OptiConstant.PERMISSION_STORAGE)

        try {
            FFmpeg.getInstance(context).loadBinary(object : FFmpegLoadBinaryResponseHandler {
                override fun onFailure() {
                    Log.e("FFMpegg", "Failed to load FFMpeg library.")
                }

                override fun onSuccess() {
                    Log.e("FFMpegg", "FFMpeg Library loaded!")
                }

                override fun onStart() {
                    Log.e("FFMpegg", "FFMpeg Started")
                }

                override fun onFinish() {
                    Log.e("FFMpegg", "FFMpeg Stopped")
                }
            })
        } catch (e: FFmpegNotSupportedException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ivClose = rootView.findViewById(R.id.iv_close)
        ivDone = rootView.findViewById(R.id.iv_done)
        ivVideoOne = rootView.findViewById(R.id.iv_video_one)
        ivVideoTwo = rootView.findViewById(R.id.iv_video_two)

        mContext = context

        ivClose.setOnClickListener {
            dismiss()
        }


        ivDone.setOnClickListener {
            var cmd: Array<String>? = null
            if (videoFileOne != null && videoFileTwo != null) {
//                dismiss()

                //output file is generated and send to video processing
                val outputFile = OptiUtils.createVideoFile(context!!)
                Log.e(tagName, "outputFile: ${outputFile.absolutePath}")

                OptiVideoEditor.with(context!!)
                    .setType(OptiConstant.MERGE_VIDEO)
                    .setFile(videoFileOne!!)
                    .setFileTwo(videoFileTwo!!)
                    .setOutputPath(outputFile.path)
                    .setCallback(this)
                    .main()
                helper?.showLoading(true)

//                cmd = arrayOf("-y", "-i", videoFileOne!!.path, "-i", videoFileTwo!!.path, "-strict", "experimental", "-filter_complex",
//                    "[0:v]scale=iw*min(1920/iw\\,1080/ih):ih*min(1920/iw\\,1080/ih), pad=1920:1080:(1920-iw*min(1920/iw\\,1080/ih))/2:(1080-ih*min(1920/iw\\,1080/ih))/2,setsar=1:1[v0];[1:v] scale=iw*min(1920/iw\\,1080/ih):ih*min(1920/iw\\,1080/ih), pad=1920:1080:(1920-iw*min(1920/iw\\,1080/ih))/2:(1080-ih*min(1920/iw\\,1080/ih))/2,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
//                    "-ab", "48000", "-ac", "2", "-ar", "22050", "-s", "1920x1080", "-vcodec", "libx264", "-crf", "27",
//                    "-q", "4", "-preset", "ultrafast", outputFile.path)

                val fileName = outputFile.absolutePath
                var file = File(fileName)
                var fileExists = file.exists()

                if (fileExists) {
                    Log.e("RUNNNN", "does exist.")
                } else {
                    Log.e("RUNNNN", "does not exist.")
                }
                Log.e("RUNNNNNN", outputFile.path.toString())
                Log.e("RUNNNNNN", outputFile.absolutePath.toString())


//                val fragmentTransaction = fragmentManager?.beginTransaction()
//                fragmentTransaction?.replace(R.id.frame_container, ActivityExoPlayer())?.commit()
            } else {
//                OptiUtils.showGlideToast(activity!!, getString(R.string.error_merge))
                Log.e("RUNNNNNN", "ELSEEEEEE")
            }


//            val ffmpeg = FFmpeg.getInstance(context)
//            ffmpeg.execute(cmd, object : ExecuteBinaryResponseHandler() {
//                override fun onStart() {
//                    Log.e("CALLLLLL", "onStart")
//                }
//
//                override fun onProgress(message: String?) {
//                    Log.e("CALLLLLL", "onProgress")
//                }
//
//                override fun onSuccess(message: String?) {
//                    Log.e("CALLLLLL", "onSuccess")
//                    Toast.makeText(mContext, "onSuccess", Toast.LENGTH_LONG).show()
//                    val intent = Intent(requireContext(), ActivityExoPlayer::class.java)
//                    val bundle = Bundle()
//                    bundle.putString("outputPath", outputFile.path)
//                    intent.putExtras(bundle)
//                    startActivity(intent)
//                }
//
//                override fun onFailure(message: String?) {
//                    Log.e("CALLLLLL", "onFailure")
//                    Toast.makeText(mContext, "onFailure", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onFinish() {
//                    Log.e("CALLLLLL", "onFinish")
//                }
//            })


        }

        ivVideoOne.setOnClickListener {
            checkPermission(OptiConstant.VIDEO_MERGE_1, Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        ivVideoTwo.setOnClickListener {
            checkPermission(OptiConstant.VIDEO_MERGE_2, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    fun downloadFileToCacheAndGallery(link: String, contentType: Media.ContentType) {

        val inputStream = downloadMedia(link)?.byteStream()
        Log.e("RUNNN", inputStream.toString())
        Log.e("RUNNN", link.toString())

        inputStream?.let {
            val mediaType =
                if (contentType == Media.ContentType.VIDEO) Media.Type.Video else Media.Type.Image
            val format = getFormat(link)

            val cache = MainApplication.mContext.saveToCacheAndCopy(it, format)!!
            val gallery =
                MainApplication.mContext.copyMediaToGallery(cache, mediaType, format, true)
            Log.e("RUNNN", cache)
            Log.e("RUNNN", gallery)
            _galleryAndCacheUri.value = arrayOf(cache, gallery)
        }
    }

    private var isFirstTimePermission: Boolean
        get() = preferences.getBoolean("isFirstTimePermission", false)
        set(isFirstTime) = preferences.edit().putBoolean("isFirstTimePermission", isFirstTime)
            .apply()

    private val isMarshmallow: Boolean
        get() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) or (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1)

    private fun checkHasPermission(
        context: Activity?,
        permissions: Array<String>?
    ): ArrayList<String> {
        permissionList = ArrayList()
        if (isMarshmallow && context != null && permissions != null) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionList.add(permission)
                }
            }
        }
        return permissionList
    }

    private fun isPermissionBlocked(context: Activity?, permissions: ArrayList<String>?): Boolean {
        if (isMarshmallow && context != null && permissions != null && isFirstTimePermission) {
            for (permission in permissions) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    return true
                }
            }
        }
        return false
    }


    private fun checkStoragePermission(permission: Array<String>) {
        val blockedPermission = checkHasPermission(activity, permission)
        if (blockedPermission != null && blockedPermission.size > 0) {
            val isBlocked = isPermissionBlocked(activity, blockedPermission)
            if (isBlocked) {
                callPermissionSettings()
            } else {
                requestPermissions(permission, OptiConstant.ADD_ITEMS_IN_STORAGE)
            }
        } else {
        }
    }

    fun checkAllPermission(permission: Array<String>) {
        val blockedPermission = checkHasPermission(activity, permission)
        if (blockedPermission != null && blockedPermission.size > 0) {
            val isBlocked = isPermissionBlocked(activity, blockedPermission)
            if (isBlocked) {
                callPermissionSettings()
            } else {
                requestPermissions(permission, OptiConstant.RECORD_VIDEO)
            }
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            videoFile = OptiUtils.createVideoFile(context!!)
            Log.v(tagName, "videoPath1: " + videoFile!!.absolutePath)
            videoUri = FileProvider.getUriForFile(
                context!!,
                "com.obs.marveleditor.provider", videoFile!!
            )
            cameraIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 240) //4 minutes
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoFile)
            startActivityForResult(cameraIntent, OptiConstant.RECORD_VIDEO)
        }
    }


    override fun setMode(mode: Int) {

    }

    override fun setFilePathFromSource(file: File) {

    }

    override fun setHelper(helper: OptiBaseCreatorDialogFragment.CallBacks) {
        this.helper = helper
    }

    override fun setDuration(duration: Long) {

    }

    private fun checkPermission(requestCode: Int, permission: String) {
        requestPermissions(arrayOf(permission), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            OptiConstant.VIDEO_MERGE_1 -> {
                for (permission in permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity as Activity,
                            permission
                        )
                    ) {
                        Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
                    } else {
                        if (ActivityCompat.checkSelfPermission(
                                activity as Activity,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            //call the gallery intent
                            val i = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            )
                            i.type = "video/*"
                            i.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("audio/*", "video/*"))
                            startActivityForResult(i, OptiConstant.VIDEO_MERGE_1)
                        } else {
                            callPermissionSettings()
                        }
                    }
                }
                return
            }

            OptiConstant.VIDEO_MERGE_2 -> {
                for (permission in permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity as Activity,
                            permission
                        )
                    ) {
                        Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
                    } else {
                        if (ActivityCompat.checkSelfPermission(
                                activity as Activity,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            //call the gallery intent
                            val i = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            )
                            i.type = "video/*"
                            i.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("audio/*", "video/*"))
                            startActivityForResult(i, OptiConstant.VIDEO_MERGE_2)
                        } else {
                            callPermissionSettings()
                        }
                    }
                }
                return
            }

            OptiConstant.ADD_ITEMS_IN_STORAGE -> {
                for (permission in permissions) {
                    Log.e("RUnnnnn", "storahee")
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity as Activity,
                            permission
                        )
                    ) {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        break
                    } else {
                        if (ActivityCompat.checkSelfPermission(
                                context!!,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            itemStorageAction()
                        } else {
                            callPermissionSettings()
                        }
                    }
                }
                return
            }

            OptiConstant.VIDEO_GALLERY -> {
                for (permission in permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity as Activity,
                            permission
                        )
                    ) {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        break
                    } else {
                        if (ActivityCompat.checkSelfPermission(
                                activity as Activity,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            //call the gallery intent
                            OptiUtils.refreshGalleryAlone(context!!)
                            val i = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            )
                            i.type = "video/*"
                            i.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*"))
                            startActivityForResult(i, OptiConstant.VIDEO_GALLERY)
                        } else {
                            callPermissionSettings()
                        }
                    }
                }
                return
            }
        }
    }

    private fun callPermissionSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context!!.applicationContext.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 300)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) return

        when (requestCode) {
            OptiConstant.VIDEO_MERGE_1 -> {
                data?.let {
                    setFilePath(resultCode, it, OptiConstant.VIDEO_MERGE_1)
                }
            }

            OptiConstant.VIDEO_MERGE_2 -> {
                data?.let {
                    setFilePath(resultCode, it, OptiConstant.VIDEO_MERGE_2)
                }
            }
        }
    }

    private fun setFilePath(resultCode: Int, data: Intent, mode: Int) {

        if (resultCode == Activity.RESULT_OK) {
            try {
                val selectedImage = data.data
                //  Log.e("selectedImage==>", "" + selectedImage)
                val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)
                val cursor = context!!.contentResolver
                    .query(selectedImage!!, filePathColumn, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val columnIndex = cursor
                        .getColumnIndex(filePathColumn[0])
                    val filePath = cursor.getString(columnIndex)
                    cursor.close()
                    if (mode == OptiConstant.VIDEO_MERGE_1) {
                        videoFileOne = File(filePath)
                        Log.e(tagName, "videoFileOne: " + videoFileOne!!.absolutePath)
                        pathVideoOne = videoFileOne!!.absolutePath
                        ivVideoOne.text = videoFileOne!!.absolutePath

//                        "/storage/emulated/0/Movies/Instagram/VID_289770909_124249_201.mp4"
//                        "/storage/emulated/0/Movies/Instagram/VID_289770909_124249_201.mp4"

                        //get thumbnail of selected video
                        bmThumbnailOne = ThumbnailUtils.createVideoThumbnail(
                            videoFileOne!!.absolutePath,
                            MediaStore.Images.Thumbnails.FULL_SCREEN_KIND
                        )

//                        ivVideoOne.setImageBitmap(bmThumbnailOne)
                    } else if (mode == OptiConstant.VIDEO_MERGE_2) {
                        videoFileTwo = File(filePath)
                        Log.e(tagName, "videoFileTwo: " + videoFileTwo!!.absolutePath)
                        pathVideoTwo = videoFileTwo!!.absolutePath
                        ivVideoTwo.text = videoFileTwo!!.absolutePath

//                        "/storage/emulated/0/Movies/Instagram/VID_289520311_080255_393.mp4"
//                        "/storage/emulated/0/Movies/Instagram/VID_289520311_080255_393.mp4"
                        //get thumbnail of selected video
                        bmThumbnailTwo = ThumbnailUtils.createVideoThumbnail(
                            videoFileTwo!!.absolutePath,
                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND
                        )

                    }
                }
            } catch (e: Exception) {
                Log.e(tagName, "Exception: ${e.localizedMessage}")
            }
        }
    }

    companion object {
        fun newInstance() = OptiMergeFragment()
    }

    override fun onProgress(progress: String) {
        Log.v(tagName, "onProgress()")
    }

    override fun onSuccess(convertedFile: File, type: String) {
        Log.v(tagName, "onSuccess()")
        helper?.showLoading(false)
        helper?.onFileProcessed(convertedFile)
    }

    override fun onFailure(error: Exception) {
        Log.e(tagName, "onFailure() ${error.localizedMessage}")
        Toast.makeText(mContext, "Video processing failed", Toast.LENGTH_LONG).show()
        helper?.showLoading(false)
    }

    override fun onNotAvailable(error: Exception) {
        Log.v(tagName, "onNotAvailable() ${error.localizedMessage}")
    }

    override fun onFinish() {
        Log.v(tagName, "onFinish()")
        helper?.showLoading(false)
    }

    private fun itemStorageAction() {
        val sessionManager = OptiSessionManager()

        if (sessionManager.isFirstTime(activity!!)) {
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_1,
                "sticker_1",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_2,
                "sticker_2",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_3,
                "sticker_3",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_4,
                "sticker_4",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_5,
                "sticker_5",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_6,
                "sticker_6",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_7,
                "sticker_7",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_8,
                "sticker_8",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_9,
                "sticker_9",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_10,
                "sticker_10",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_11,
                "sticker_11",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_12,
                "sticker_12",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_13,
                "sticker_13",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_14,
                "sticker_14",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_15,
                "sticker_15",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_16,
                "sticker_16",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_17,
                "sticker_17",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_18,
                "sticker_18",
                context!!
            )
            OptiUtils.copyFileToInternalStorage(
                R.drawable.sticker_19,
                "sticker_19",
                context!!
            )

//            OptiUtils.copyFontToInternalStorage(
//               R.font.roboto_black,
//                "roboto_black",
//                context!!
//            )

            sessionManager.setFirstTime(activity!!, false)
        }
    }

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



interface OptiDialogueHelper {
    fun setHelper(helper: OptiBaseCreatorDialogFragment.CallBacks)
    fun setMode(mode: Int)
    fun setFilePathFromSource(file: File)
    fun setDuration(duration: Long)
}

interface ApiInterface {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

}

