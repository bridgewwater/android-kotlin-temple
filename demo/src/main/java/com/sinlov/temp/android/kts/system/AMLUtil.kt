package com.sinlov.temp.android.kts.system

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.collection.ArrayMap
import timber.log.Timber

/**
 * Activity Lifecycle 管理类
 *
 * <br/>
 * 在 Application 的声明周期 {@link Application#onCreate()} 中使用
 *
 * <pre>
 *        // Activity 栈管理初始化，默认会读取 apk debug 状态，打开日志
 *        AMLUtil.INSTANCE.init(this)
 *
 *        // 也可以强制关闭 Debug
 *        AMLUtil.INSTANCE.setDebug(false)
 *
 * </pre>
 *
 * 功能
 * <pre>
 *        // 安全获取 application
 *        AMLUtil.INSTANCE.getApplication();
 *        // 获取当前栈顶 Activity
 *        AMLUtil.INSTANCE.getTopActivity()
 *        // 内存优化，销毁掉所有的界面
 *        AMLUtil.INSTANCE.finishAllActivities();
 *        // 内存优化，销毁除登录页之外的所有界面
 *        AMLUtil.INSTANCE.finishAllActivities(LoginActivity.class);
 *        // 判断当前应用是否处于前台状态
 *        AMLUtil.INSTANCE.isForeground()
 * </pre>
 * Created by sinlov on 2021/5/6.
 */
class AMLUtil : ActivityLifecycleCallbacks {

    private val mActivitySet = ArrayMap<String, Activity>()

    /**
     * 当前应用上下文对象
     */
    private var mApplication: Application? = null

    /**
     * 最后一个可见 Activity 标记
     */
    private var mLastVisibleTag: String? = null

    /**
     * 最后一个不可见 Activity 标记
     */
    private var mLastInvisibleTag: String? = null

    /**
     * 当前是否在 Debug 模式
     */
    private var isDebug = false

    @Volatile
    private var hasSelfDebugCheck = false

    @Volatile
    private var selfDebugCache = false

    fun isSelfDebug(context: Context): Boolean {
        if (hasSelfDebugCheck) {
            return selfDebugCache
        }
        synchronized(AMLUtil::class.java) {
            try {
                val info = context.applicationInfo
                selfDebugCache = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
                hasSelfDebugCheck = true
                return selfDebugCache
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }


    fun init(application: Application): AMLUtil? {
        mApplication = application
        mApplication!!.registerActivityLifecycleCallbacks(this)
        // 默认情况会读取 Apk 是否在 Debug
        isDebug = this.isSelfDebug(application.applicationContext)
        return this
    }

    private fun checkInit() {
        requireNotNull(mApplication) { "not use ActivityManager.init(application)" }
    }

    fun isDebug(): Boolean {
        return isDebug
    }

    fun setDebug(debug: Boolean) {
        isDebug = debug
    }

    /**
     * 获取 Application 对象
     */
    fun getApplication(): Application? {
        checkInit()
        return mApplication
    }

    /**
     * 获取栈顶的 Activity
     */
    fun getTopActivity(): Activity? {
        checkInit()
        return mActivitySet[mLastVisibleTag]
    }


    /**
     * 判断当前应用是否处于前台状态, 通过最后一个 Activity 是否时记录的
     */
    fun isForeground(): Boolean {
        checkInit()
        // 如果最后一个可见的 Activity 和最后一个不可见的 Activity 是同一个的话
        if (mLastVisibleTag == mLastInvisibleTag) {
            return false
        }
        val activity = getTopActivity()
        return activity != null
    }

    /**
     * 销毁所有的 Activity
     */
    fun finishAllActivities() {
        finishAllActivities((null as Class<out Activity?>?)!!)
    }


    /**
     * 销毁所有的 Activity，除这些 Class 之外的 Activity
     */
    fun finishAllActivities(vararg classArray: Class<out Activity?>) {
        checkInit()
        val keys: Array<String> = mActivitySet.keys.toTypedArray<String>()
        for (key in keys) {
            val activity = mActivitySet[key]
            if (activity != null && !activity.isFinishing) {
                var whiteClazz = false
                if (classArray != null) {
                    for (clazz in classArray) {
                        if (activity.javaClass == clazz) {
                            whiteClazz = true
                        }
                    }
                }
                // 如果不是白名单上面的 Activity 就销毁掉
                if (!whiteClazz) {
                    activity.finish()
                    mActivitySet.remove(key)
                }
            }
        }
    }

    /**
     * 获取一个对象的独立无二的标记, 同 object.getName() + Hex(object.hashCode())
     */
    private fun getObjectTag(`object`: kotlin.Any): kotlin.String? {
        // 对象所在的包名 + 对象的内存地址
        return `object`.javaClass.name + java.lang.Integer.toHexString(`object`.hashCode())
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (isDebug) {
            Timber.i("%s - onCreate", activity.javaClass.simpleName)
        }
        mLastVisibleTag = getObjectTag(activity)
        mActivitySet[getObjectTag(activity)] = activity
    }

    override fun onActivityStarted(activity: Activity) {
        if (isDebug) {
            Timber.i("%s - onStart", activity.javaClass.simpleName)
        }
        mLastVisibleTag = getObjectTag(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        if (isDebug) {
            Timber.i("%s - onResume", activity.javaClass.simpleName)
        }
        mLastVisibleTag = getObjectTag(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        if (isDebug) {
            Timber.i("%s - onPause", activity.javaClass.simpleName)
        }
        mLastInvisibleTag = getObjectTag(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        if (isDebug) {
            Timber.i("%s - onStop", activity.javaClass.simpleName)
        }
        mLastInvisibleTag = getObjectTag(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (isDebug) {
            Timber.i("%s - onSaveInstanceState", activity.javaClass.simpleName)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (isDebug) {
            Timber.i("%s - onDestroy", activity.javaClass.simpleName)
        }
        mActivitySet.remove(getObjectTag(activity))
        mLastInvisibleTag = getObjectTag(activity)
        if (getObjectTag(activity) == mLastVisibleTag) {
            // 清除当前标记
            mLastVisibleTag = null
        }
    }

    /**
     * <br/>
     * 在 Application 的声明周期 {@link Application#onCreate()} 中使用
     *
     * <pre>
     *        // Activity 栈管理初始化，默认会读取 apk debug 状态，打开日志
     *        AMLUtil.INSTANCE.init(this)
     *
     *        // 也可以强制关闭 Debug
     *        AMLUtil.INSTANCE.setDebug(false)
     *
     * </pre>
     *
     * 功能
     * <pre>
     *        // 安全获取 application
     *        AMLUtil.INSTANCE.getApplication();
     *        // 获取当前栈顶 Activity
     *        AMLUtil.INSTANCE.getTopActivity()
     *        // 内存优化，销毁掉所有的界面
     *        AMLUtil.INSTANCE.finishAllActivities();
     *        // 内存优化，销毁除登录页之外的所有界面
     *        AMLUtil.INSTANCE.finishAllActivities(LoginActivity.class);
     *        // 判断当前应用是否处于前台状态
     *        AMLUtil.INSTANCE.isForeground()
     * </pre>
     */
    companion object {
        /**
         * lazy @kotlin.internal.InlineOnly
         */
        val INSTANCE: AMLUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AMLUtil()
        }
    }
}