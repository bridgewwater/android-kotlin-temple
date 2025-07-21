package test

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.sinlov.kotlin.android.demo.MainActivity
import org.junit.After
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

public abstract class RoboActKtTemp : RoboKtTemp() {
    protected var activityController: ActivityController<MainActivity>? = null
    protected var activityScenario: ActivityScenario<MainActivity>? = null

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        // Create new activity http://robolectric.org/androidx_test/
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario?.moveToState(Lifecycle.State.CREATED)
        this.activityController =
            Robolectric.buildActivity(MainActivity::class.java).create().start().resume().visible()
    }

    @After
    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()
        // Destroy activity
        activityScenario!!.moveToState(Lifecycle.State.DESTROYED)
    }
}