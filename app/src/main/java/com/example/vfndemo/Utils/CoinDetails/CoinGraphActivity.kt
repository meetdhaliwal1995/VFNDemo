package com.example.vfndemo.Utils.CoinDetails

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vfndemo.Utils.CoinDetails.Model.DataGraph
import com.example.vfndemo.Utils.CoinDetails.Model.UserTimeLineModel
import com.example.vfndemo.databinding.FragmentCoinBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import kotlin.math.log

class CoinGraphActivity : AppCompatActivity() {
    private var _binding: FragmentCoinBinding? = null
    val binding get() = _binding
    val BASE_URL = "https://api02.sc-stage.com/"
    lateinit var coinAdapter: CoinGraphAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentCoinBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        coinAdapter = CoinGraphAdapter()
        linearLayoutManager = LinearLayoutManager(this)
        binding?.rvMain?.layoutManager = linearLayoutManager
        binding?.rvMain?.adapter = coinAdapter
        getData()
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.coingecko.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getCoinChart("bitcoin", "10", "usd")

        retrofitData.enqueue(object : Callback<GraphModel> {
            override fun onResponse(
                call: Call<GraphModel>,
                response: Response<GraphModel>
            ) {
                response.body()?.let {
                    coinAdapter.addItem(listOf(it))
                    coinAdapter.setIntervalPeriod("5")
                }
            }

            override fun onFailure(call: Call<GraphModel>, t: Throwable) {
            }

        })
    }

}

interface ApiInterface {
    @GET("/api/v3/coins/{id}/market_chart")
    fun getCoinChart(
        @Path("id") id: String,
        @Query("days") days: String, @Query("vs_currency") currency: String = "usd"
    ): Call<GraphModel>

    @GET("/api/v1/coin/{id}/coin-history")
    fun getCoinGraph(
        @Path("id") id: String,
        @Query("fromTime") days: String, @Query("toTime") currency: String
    ): Call<DataGraph>

    @GET("/api/v1/signal/timelines")
    fun getCoinTimeLine(): Call<UserTimeLineModel>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl:String): Response<ResponseBody>

}