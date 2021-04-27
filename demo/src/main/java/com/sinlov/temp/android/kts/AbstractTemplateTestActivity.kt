package com.sinlov.temp.android.kts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.sinlov.temp.android.kts.action.*
import kotlin.math.pow
import kotlin.random.Random

/**
 * 非侵入形 Activity 模板
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
abstract class AbstractTemplateTestActivity : AppCompatActivity(),
        TempActivityAction,
        TempBundleAction,
        TempHandlerAction,
        TempKeyboardAction,
        TempToastAction {

    protected open lateinit var TAG: String

    /**
     * Activity 回调集合
     */
    private var mActivityCallbacks: SparseArray<OnActivityCallback>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
        initActivity()
        initData(savedInstanceState)
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.removeALLCallbacks()
    }

    override fun finish() {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(currentFocus)
        super.finish()
    }

    protected open fun initActivity() {
        if (beforeSetContentView()) {
            initLayout()
            initView()
        }
    }

    protected open fun initLayout() {
        if (getLayoutId() > 0) {
            setContentView(getLayoutId())
        } else {
            val contentView: View = onSetContentView()
                    ?: throw NullPointerException("onSetContentView return is null")
            setContentView(contentView)
        }
        initSoftKeyboard()
    }

    /**
     * 无论返回啥，都会执行 {@link #initData(Bundle)}
     *
     * @return true 继续响应 <br/>
     *         {@link #getLayoutId()} or {@link #onSetContentView()} and {@link #initView()}
     */
    abstract fun beforeSetContentView(): Boolean

    /**
     * 优先通过布局 ID 初始化 onCreate setContentView() {@link AbstractTemplateTestActivity#initLayout()}
     */
    abstract fun getLayoutId(): Int

    /**
     * 如果 {@link AbstractTemplateTestActivity#getLayoutId()} 小于 1
     * <br/>
     * 不通过布局 ID 初始化 onCreate setContentView() in {@link AbstractTemplateTestActivity#initLayout()}
     * <br/>
     * 使用示例
     * <pre>
     *     Binding.inflate(getLayoutInflater());
     *     return binding.getRoot();
     * </pre>
     *
     * @return view
     */
    abstract fun onSetContentView(): View?

    /**
     * 初始化控件
     * <br/>
     * after {@link AbstractTemplateTestActivity#getLayoutId()} or {@link AbstractTemplateTestActivity#onSetContentView()}
     */
    abstract fun initView()

    /**
     * 初始化数据 在生命周期 onCreate
     */
    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 和 setContentView 对应的方法
     */
    open fun getContentView(): ViewGroup {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    /**
     * 初始化软键盘
     */
    protected open fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(currentFocus)
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun getBundle(): Bundle? {
        return intent.extras
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        // 隐藏软键，避免内存泄漏
        hideKeyboard(currentFocus)
        // startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options)
    }

    /**
     * startActivityForResult 优化
     *
     * @param clazz    [Activity]
     * @param callback [OnActivityCallback]
     */
    open fun startActivityForResult(clazz: Class<out Activity?>?, callback: OnActivityCallback?) {
        startActivityForResult(Intent(this, clazz), null, callback)
    }

    /**
     * startActivityForResult 优化封装
     *
     * @param intent   [Intent]
     * @param callback [OnActivityCallback]
     */
    open fun startActivityForResult(intent: Intent?, callback: OnActivityCallback?) {
        startActivityForResult(intent, null, callback)
    }

    /**
     * requestCode 为自动生成，规则为 2 的 16 次方以内 随机
     *
     * @param intent   [Intent]
     * @param bundle   [Bundle]
     * @param callback [OnActivityCallback]
     */
    open fun startActivityForResult(intent: Intent?, bundle: Bundle?, callback: OnActivityCallback?) {
        if (mActivityCallbacks == null) {
            mActivityCallbacks = SparseArray(1)
        }
        // 请求码必须在 2 的 16 次方以内
        val requestCode = Random.nextInt(2.0.pow(16.0).toInt())
        mActivityCallbacks!!.put(requestCode, callback)
        startActivityForResult(intent, requestCode, bundle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var callback: OnActivityCallback
        if (mActivityCallbacks != null) {
            if (mActivityCallbacks!!.get(requestCode).also { callback = it } != null) {
                callback.onActivityResult(resultCode, data)
                mActivityCallbacks!!.remove(requestCode)
                return
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}