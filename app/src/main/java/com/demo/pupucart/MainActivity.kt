package com.demo.pupucart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run {
            recycler_one.layoutManager = LinearLayoutManager(this)
            val adapter = AAdapter()
            val list = arrayListOf<String>()
            repeat(3) {
                list.add("哈哈哈")
            }
            recycler_one.adapter = adapter
            adapter.dataList.clear()
            adapter.dataList.addAll(list)
            recycler_one.adapter = adapter
        }
        run {
            recycler_two.layoutManager = LinearLayoutManager(this)
            val adapter = AAdapter()
            val list = arrayListOf<String>()
            repeat(20) {
                list.add("嘻嘻嘻")
            }
            adapter.dataList.clear()
            adapter.dataList.addAll(list)
            adapter.click = {
                val adapterOne = recycler_one.adapter as AAdapter
                adapterOne.dataList.add(0, "嘻嘻嘻: ${System.currentTimeMillis()}")
                adapterOne.notifyDataSetChanged()
                if (recycler_one.translationY != 0f) {
                    recycler_one.translationY -= 120
                }
            }
            recycler_two.adapter = adapter
        }
    }
}