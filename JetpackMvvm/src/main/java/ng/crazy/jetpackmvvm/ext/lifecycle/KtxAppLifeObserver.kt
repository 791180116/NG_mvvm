package ng.crazy.jetpackmvvm.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ng.crazy.jetpackmvvm.callback.livedata.BooleanLiveData

/**
 * 描述　:
 */
object KtxAppLifeObserver : LifecycleObserver {

    var isForeground = BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }

}