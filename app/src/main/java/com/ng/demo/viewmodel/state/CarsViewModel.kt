package com.ng.demo.viewmodel.state

import androidx.lifecycle.MutableLiveData
import com.ng.demo.data.model.bean.CarBean
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel

class CarsViewModel : BaseViewModel() {
    var carBeans: MutableLiveData<MutableList<CarBean>> = MutableLiveData()

    fun getCarList() {
        val cars = ArrayList<CarBean>()
        for (i in 1..20) {
            cars.add(
                CarBean(
                    序号 = "No.$i",
                    vin = "vin_$i",
                    状态 = if (i % 2 == 0) "良" else "优",
                    电量 = "$i%",
                    里程 = "${i * 10}"
                )
            )
        }
        carBeans.value = cars
    }
}