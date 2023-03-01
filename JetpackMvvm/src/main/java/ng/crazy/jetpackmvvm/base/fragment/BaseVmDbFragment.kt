package ng.crazy.jetpackmvvm.base.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 描述　: ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>() {

    //该类绑定的ViewDataBinding
    protected lateinit var mDatabind: DB

    @Suppress("UNCHECKED_CAST")
    override fun initDataBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments.filterIsInstance<Class<DB>>()
            val method = clazz[1].getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            //val clazz = type.actualTypeArguments[0] as Class<DB>
            //val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mDatabind = method.invoke(null, inflater, container, false) as DB
            mDatabind.lifecycleOwner = this
            return mDatabind.root
        } else {
            throw Exception("View is null,check VB(ViewBinding)")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabind.unbind()
    }
}