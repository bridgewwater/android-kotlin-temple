package com.sinlov.temp.android.kts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Constructor

/**
 * <pre>
 *     ViewModelProvider(this, CtxViewModelFactory(this))
 * </pre>
 * Created by sinlov on 2021/5/6.
 */
class CtxViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            for (constructor in modelClass.constructors) {
                if (arrayOf(Context::class.java).contentEquals(constructor.parameterTypes)) {
                    return (constructor as Constructor<T>).newInstance(context)
                }
            }
            return modelClass.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}