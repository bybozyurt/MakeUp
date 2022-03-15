package com.example.makeup.ui.base


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity() {

    protected var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getContentViewLayoutResId())
        mBinding?.lifecycleOwner = this
        populateUI(savedInstanceState)
    }

}

