package com.example.vfndemo.Utils.MergeVideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vfndemo.databinding.OptiLayoutCustomExoPlayerBinding
import java.io.File

class OpenPlayerFragment: Fragment(), OptiFFMpegCallback {
    private var _binding: OptiLayoutCustomExoPlayerBinding? = null
    val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = OptiLayoutCustomExoPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.ibGallery?.setOnClickListener {
////            OptiMergeFragment.newInstance().apply {
////                setHelper(this@OpenPlayerFragment)
////            }.show(requireFragmentManager(), "OptiMergeFragment")
//        }

    }

    override fun onProgress(progress: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(convertedFile: File, type: String) {
        TODO("Not yet implemented")
    }

    override fun onFailure(error: Exception) {
        TODO("Not yet implemented")
    }

    override fun onNotAvailable(error: Exception) {
        TODO("Not yet implemented")
    }

    override fun onFinish() {
        TODO("Not yet implemented")
    }
}