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
 * Activity Lifecycle management
 *
 * <br/>
 * use at Application [Application#onCreate()] to init
 *
 * <pre>
 *        // Activity stack management init, will open log which apk debuggable status is true
 *        AMLUtil.INSTANCE.init(this)
 *           .setDebug(true)
 *
 *        // can force close Debug
 *        AMLUtil.INSTANCE.setDebug(false)
 *
 * </pre>
 * function
 * <pre>
 *        // safe way to get application
 *        AMLUtil.INSTANCE.getApplication();
 *        // get top Activity
 *        AMLUtil.INSTANCE.getTopActivity()
 *        // memory optimization, destroy all activity
 *        AMLUtil.INSTANCE.finishAllActivities();
 *        // memory optimization, destroy all activity except LoginActivity.class
 *        AMLUtil.INSTANCE.finishAllActivities(LoginActivity.class);
 *        // Determines whether the current application is in the foreground
 *        AMLUtil.INSTANCE.isForeground()
 * </pre>
 * Created by sinlov on 2021/5/6.
 */
class AMLUtil : ActivityLifecycleCallbacks {

    private val mActivitySet = ArrayMap<String, Activity>()

    /**
     * current application
     */
    private var mApplication: Application? = null

    /**
     * tag of last Visible Activity
     */
    private var mLastVisibleTag: String? = null

    /**
     * tag of last Invisible Activity
     */
    private var mLastInvisibleTag: String? = null

    /**
     * flag of AMLUtil debug
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


    fun init(application: Application): AMLUtil {
        mApplication = application
        mApplication!!.registerActivityLifecycleCallbacks(this)
        // By default, it reads whether APK is DEBUG
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
     * get current Application
     */
    fun getApplication(): Application? {
        checkInit()
        return mApplication
    }

    /**
     * get top Activity of visible
     */
    fun getTopActivity(): Activity? {
        checkInit()
        return mActivitySet[mLastVisibleTag]
    }


    /**
     * Determining whether the current application is in the foreground by recording the last visible activity
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
     * destroy all activity
     */
    fun finishAllActivities() {
        finishAllActivities((null as Class<out Activity?>?)!!)
    }


    /**
     * destroy all activity except classArray array
     */
    fun finishAllActivities(vararg classArray: Class<out Activity?>) {
        checkInit()
        val keys: Array<String> = mActivitySet.keys.toTypedArray<String>()
        for (key in keys) {
            val activity = mActivitySet[key]
            if (activity != null && !activity.isFinishing) {
                var whiteClazz = false
                for (clazz in classArray) {
                    if (activity.javaClass == clazz) {
                        whiteClazz = true
                    }
                }
                // If the activity is not on the whitelist, destroy it
                if (!whiteClazz) {
                    activity.finish()
                    mActivitySet.remove(key)
                }
            }
        }
    }

    /**
     * Gets a unique token for an object, as: object.getName() + Hex(object.hashCode())
     */
    private fun getObjectTag(`object`: kotlin.Any): kotlin.String? {
        // The class full name + the memory address of hashcode
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
            // clear the current flag
            mLastVisibleTag = null
        }
    }

    /**
     * Activity Lifecycle management
     *
     * <br/>
     * use at Application [Application#onCreate()] to init
     *
     * <code>
     *
     *        // Activity stack management init, will open log which apk debuggable status is true
     *        AMLUtil.INSTANCE.init(this)
     *           .setDebug(true)
     *
     *        // can force close Debug
     *        AMLUtil.INSTANCE.setDebug(false)
     *
     * </code>
     * function
     * <code>
     *
     *        // safe way to get application
     *        AMLUtil.INSTANCE.getApplication();
     *
     *        // get top Activity
     *        AMLUtil.INSTANCE.getTopActivity()
     *
     *        // memory optimization, destroy all activity
     *        AMLUtil.INSTANCE.finishAllActivities();
     *
     *        // memory optimization, destroy all activity except LoginActivity.class
     *        AMLUtil.INSTANCE.finishAllActivities(LoginActivity.class);
     *
     *        // Determines whether the current application is in the foreground
     *        AMLUtil.INSTANCE.isForeground()
     * </code>
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