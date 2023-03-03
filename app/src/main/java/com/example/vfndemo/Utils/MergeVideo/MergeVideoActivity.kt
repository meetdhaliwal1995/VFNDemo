package com.example.vfndemo.Utils.MergeVideo

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.vfndemo.R
import com.example.vfndemo.databinding.MergeVideoLayoutBinding
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException


class MergeVideoActivity : AppCompatActivity() {

    val REQUEST_TAKE_GALLERY_VIDEO = 11

    private var _binding: MergeVideoLayoutBinding? = null
    val binding get() = _binding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MergeVideoLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, OptiMergeFragment()).commit()


//        binding?.button?.setOnClickListener {
//            val intent = Intent()
//            intent.type = "video/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(
//                Intent.createChooser(intent, "Select Video"),
//                REQUEST_TAKE_GALLERY_VIDEO
//            )
//        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
//                val selectedImageUri: Uri? = data?.data
//
//                // OI FILE Manager
//                val filemanagerstring = selectedImageUri?.path
//
//                // MEDIA GALLERY
//                val selectedImagePath = getPath(selectedImageUri)
//                Log.e("PRINTTTT", selectedImageUri.toString())
//            }
//        }
//    }
//
//    fun getPath(uri: Uri?): String? {
//        val projection = arrayOf(MediaStore.Video.Media.DATA)
//        val cursor: Cursor? = contentResolver.query(uri!!, projection, null, null, null)
//        return if (cursor != null) {
//            val column_index: Int = cursor
//                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
//            cursor.moveToFirst()
//            cursor.getString(column_index)
//        } else null
//    }

}



























//val path = Paths.get("video.mp4").toAbsolutePath().toString()
//val file = File(applicationContext.filesDir, "video.mp4")
////        val fileName = "C:\\Users\\ammdh\\AndroidStudioProjects\\VFNDemo\\app\\src\\main\\java\\com\\example\\vfndemo\\Utils\\MergeVideo\\video.mp4"
////        val fileNamee = "C:\\Users\\ammdh\\AndroidStudioProjects\\VFNDemo\\app\\src\\main\\java\\com\\example\\vfndemo\\Utils\\MergeVideo\\videoo.mp4"
////        var file = File(fileName)
//var fileExists = file.exists()
//Log.e("IFFPAth", file.toString())
//
//if (fileExists) {
////            print("$fileName does exist.")
//    Log.e("IFF", "EXIXT")
//} else {
//    Log.e("IFF", "NOT EXIXT")
//
////            print("$fileName does not exist.")
//}