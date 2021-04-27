package com.sinlov.temp.android.kts

/**
 * 普通模板
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
abstract class AbsTemplateActivity : AbstractTemplateTestActivity() {
    override fun beforeSetContentView(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return -1
    }
}