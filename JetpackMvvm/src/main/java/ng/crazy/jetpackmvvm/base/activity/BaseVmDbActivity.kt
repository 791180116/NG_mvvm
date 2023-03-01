package ng.crazy.jetpackmvvm.base.activity

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 描述　: 包含ViewModel 和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    lateinit var mDatabind: DB

    /**
     * 创建DataBinding
     */
    @Suppress("UNCHECKED_CAST")
    override fun initDataBind() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz1 = type.actualTypeArguments.filterIsInstance<Class<DB>>()
            val method = clazz1[1].getDeclaredMethod("inflate", LayoutInflater::class.java)
            //val clazz = type.actualTypeArguments[0] as Class<DB>
            //val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mDatabind = method.invoke(null, layoutInflater) as DB
            setContentView(mDatabind.root)
            mDatabind.lifecycleOwner = this
        } else {
            throw Exception("View is null,check VB(ViewBinding)")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabind.unbind()
    }
}