package com.sinlov.temp.android.kts.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.IntRange

/**
 * 剪切板工具
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
class ClipboardUtils {
    companion object {
        fun copy2clipboard(context: Context, content: String) {
            var ctx = content
            ctx = ctx.trim { it <= ' ' }
            val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(ctx.trim { it <= ' ' }, ctx.trim { it <= ' ' })
            cmb.setPrimaryClip(clipData)
        }

        fun item(context: Context, @IntRange(from = 0, to = 10) count: Int): CharSequence? {
            val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val primaryClip = cmb.primaryClip ?: return ""
            return if (primaryClip.itemCount < count) {
                ""
            } else primaryClip.getItemAt(count).text
        }

        fun label(context: Context): String {
            val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val primaryClip = cmb.primaryClip ?: return ""
            val description = primaryClip.description ?: return ""
            val label = description.label ?: return ""
            return label.toString()
        }
    }
}
