package com.sinlov.temp.android.kts.action

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Bundle 参数意图
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 2021/4/26.
 */
interface TempBundleAction {

    fun getBundle(): Bundle?

    @JvmDefault
    fun getInt(name: String?): Int {
        return getInt(name, 0)
    }

    @JvmDefault
    fun getInt(name: String?, defaultValue: Int): Int {
        val bundle = getBundle() ?: return defaultValue
        return bundle.getInt(name, defaultValue)
    }

    @JvmDefault
    fun getLong(name: String?): Long {
        return getLong(name, 0)
    }

    @JvmDefault
    fun getLong(name: String?, defaultValue: Int): Long {
        val bundle = getBundle() ?: return defaultValue.toLong()
        return bundle.getLong(name, defaultValue.toLong())
    }

    @JvmDefault
    fun getFloat(name: String?): Float {
        return getFloat(name, 0)
    }

    @JvmDefault
    fun getFloat(name: String?, defaultValue: Int): Float {
        val bundle = getBundle() ?: return defaultValue.toFloat()
        return bundle.getFloat(name, defaultValue.toFloat())
    }

    @JvmDefault
    fun getDouble(name: String?): Double {
        return getDouble(name, 0)
    }

    @JvmDefault
    fun getDouble(name: String?, defaultValue: Int): Double {
        val bundle = getBundle() ?: return defaultValue.toDouble()
        return bundle.getDouble(name, defaultValue.toDouble())
    }

    @JvmDefault
    fun getBoolean(name: String?): Boolean {
        return getBoolean(name, false)
    }

    @JvmDefault
    fun getBoolean(name: String?, defaultValue: Boolean): Boolean {
        val bundle = getBundle() ?: return defaultValue
        return bundle.getBoolean(name, defaultValue)
    }

    @JvmDefault
    fun getString(name: String?): String? {
        val bundle = getBundle() ?: return null
        return bundle.getString(name)
    }

    @JvmDefault
    fun <P : Parcelable?> getParcelable(name: String?): P? {
        val bundle = getBundle() ?: return null
        return bundle.getParcelable(name)
    }

    @Suppress("UNCHECKED_CAST")
    @JvmDefault
    fun <S : Serializable?> getSerializable(name: String?): S? {
        val bundle = getBundle() ?: return null
        return bundle.getSerializable(name) as S?
    }

    @JvmDefault
    fun getStringArrayList(name: String?): ArrayList<String?>? {
        val bundle = getBundle() ?: return null
        return bundle.getStringArrayList(name)
    }

    @JvmDefault
    fun getIntegerArrayList(name: String?): ArrayList<Int?>? {
        val bundle = getBundle() ?: return null
        return bundle.getIntegerArrayList(name)
    }
}