package com.example.vfndemo.Utils.CoinDetails

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
//import com.example.vfndemo.Utils.CoinDetails.CoinRepo.CoinViewModel
import com.example.vfndemo.databinding.FragmentCoinBinding

class FragmentCoin : AppCompatActivity() {
    private var _binding: FragmentCoinBinding? = null
    val binding get() = _binding
    private var graphPosition: Int? = null
    val coinGraphAdapter = CoinGraphAdapter()
    private var graphIntervalPeriod: String = "1"
//    lateinit var tipViewModel: CoinViewModel


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = FragmentCoinBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

    private fun observerGraphData() {

//        tipViewModel.getCoinChart("bitcoin" ?: "", graphIntervalPeriod)
//            .observe(viewLifecycleOwner) {
//                when (it) {
//                    is com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.ContentLoaded -> {
//                        graphPosition?.let { position ->
//                            coinGraphAdapter.clear()
//                            coinGraphAdapter.notifyItemChanged(position)
//                            coinGraphAdapter.setIntervalPeriod(graphIntervalPeriod)
//                            coinGraphAdapter.addItem(listOf(it.value))
//                            coinGraphAdapter.notifyItemChanged(position)
//                        } ?: run {
//                        }
//
//                    }
//                    is com.example.vfndemo.Utils.CoinDetails.CoinRepo.ViewState.LoadFailed -> {
//                    }
//                    else -> {
//                        //Do nothing
//                    }
//                }
//            }
    }
}