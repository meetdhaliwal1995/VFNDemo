package com.example.vfndemo.Utils.CoinDetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {


    var adapterDataList: MutableList<T> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context)
            .inflate(getLayoutId(), parent, false)
        return doCreateViewHolder(view)
    }

    abstract fun doCreateViewHolder(view: View): BaseViewHolder<T>

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if(adapterDataList.size > 0)
        holder.bind(adapterDataList[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun addItem(item: List<T>){
        adapterDataList.addAll(item)
        notifyDataSetChanged()
    }

    open fun refreshList(item: List<T>){
        adapterDataList.clear()
        adapterDataList.addAll(item)
        notifyDataSetChanged()
    }

    open fun updateData(item: List<T>){
        adapterDataList.clear()
        adapterDataList.addAll(item)
        notifyDataSetChanged()
    }

    fun clear(){
        adapterDataList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return adapterDataList.size
    }
}
