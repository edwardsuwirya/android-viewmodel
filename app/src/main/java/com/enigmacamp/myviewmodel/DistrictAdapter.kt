package com.enigmacamp.myviewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.enigmacamp.myviewmodel.databinding.DistrictItemBinding

class DistrictAdapter(val districtState: ResourceState?) : BaseAdapter() {
    private var districts = arrayListOf("Daftar Kecamatan")

    init {
        districtState?.data?.let {
            districts.clear()
            districts.addAll(it as List<String>)
        }
    }

    override fun getCount() = districts.size

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(p2?.context)
        val view: View
        districtState?.apply {
            when (districtState.status) {
                ResourceStatus.LOADING -> {
                    view = inflater.inflate(R.layout.loading_in_spinner, p2, false)
                    return view
                }
                ResourceStatus.SUCCESS -> {
                    view = inflater.inflate(R.layout.district_item, p2, false)
                    val itemBinding = DistrictItemBinding.bind(view)
                    itemBinding.apply {
                        districtTextView.setText(districts[p0])
                    }
                    return view
                }
                ResourceStatus.FAIL -> {
                    view = inflater.inflate(R.layout.district_item, p2, false)
                    val itemBinding = DistrictItemBinding.bind(view)
                    itemBinding.apply {
                        districtTextView.setText("Gagal...")
                    }
                    return view
                }
            }
        }
        view = inflater.inflate(R.layout.district_item, p2, false)
        val itemBinding = DistrictItemBinding.bind(view)
        itemBinding.apply {
            districtTextView.setText(districts[p0])
        }
        return view

    }
}