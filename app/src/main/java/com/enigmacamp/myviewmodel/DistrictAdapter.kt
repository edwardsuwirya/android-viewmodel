package com.enigmacamp.myviewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.enigmacamp.myviewmodel.databinding.DistrictItemBinding

class DistrictAdapter(val districtList: List<String>) : BaseAdapter() {
    override fun getCount() = districtList.size

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(p2?.context)
        val itemBinding = DistrictItemBinding.inflate(inflater, p2, false)
        itemBinding.apply {
            districtTextView.setText(districtList[p0])
        }
        return itemBinding.root
    }
}