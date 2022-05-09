package ng.crazy.jetpackmvvm.callback.livedata

import androidx.lifecycle.MutableLiveData


/**
 * 描述　:自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}