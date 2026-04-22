package com.example.musiccreater.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding
            ?: throw IllegalStateException("Binding accessed outside of lifecycle")

    protected val isViewAlive: Boolean
        get() = _binding != null &&
                isAdded &&
                !isDetached &&
                !isRemoving &&
                !requireActivity().isFinishing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val clazz = superclass.actualTypeArguments[0] as Class<VB>
        val inflateMethod = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = inflateMethod.invoke(null, inflater, container, false) as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    protected abstract fun init()

    protected fun runOnUiSafe(block: () -> Unit) {
        if (!isViewAlive) return
        val activity = activity ?: return
        if (activity.isFinishing || activity.isDestroyed) return
        activity.runOnUiThread {
            if (isViewAlive) {
                try {
                    block()
                } catch (e: Exception) {
                    android.util.Log.e("BaseFragment", "runOnUiSafe error", e)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}