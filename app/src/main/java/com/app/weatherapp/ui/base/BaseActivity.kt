package com.app.weatherapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val bindingVariable: Int
    lateinit var binding: T

    @Inject
    lateinit var mViewModel: V

    abstract fun setObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setObserver()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            setVariable(bindingVariable, mViewModel)
            executePendingBindings()
        }
        binding.lifecycleOwner = this
    }
}