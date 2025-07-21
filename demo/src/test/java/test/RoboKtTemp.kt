package test

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O])
public abstract class RoboKtTemp : TestTemp() {
    protected
    val CTX_APP: Context = ApplicationProvider.getApplicationContext()

    protected
    val APPLICATION: Application = ApplicationProvider.getApplicationContext()


    override fun setUp() {
        super.setUp()
    }

    override fun tearDown() {
        super.tearDown()
    }
}