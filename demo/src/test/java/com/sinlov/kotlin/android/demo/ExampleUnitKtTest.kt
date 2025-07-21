package com.sinlov.kotlin.android.demo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import test.RoboKtTemp


public class ExampleUnitKtTest : RoboKtTemp() {
    @org.junit.Test
    public fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        assertNotNull(CTX_APP)
    }
}