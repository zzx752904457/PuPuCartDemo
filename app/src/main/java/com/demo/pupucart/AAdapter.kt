package com.demo.pupucart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val dataList = arrayListOf<String>()
    var click: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_one, parent, false)
        return ViewHolderA(view, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderA -> {
                holder.bindData(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderA(itemView: View, adapter: AAdapter) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                adapter.click?.invoke()
            }
        }

        fun bindData(text: String) {
            itemView.findViewById<TextView>(R.id.tv).text = text
        }
    }
}
