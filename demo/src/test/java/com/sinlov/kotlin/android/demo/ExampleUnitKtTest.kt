package com.sinlov.kotlin.android.demo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import test.RoboActKtTemp


class ExampleUnitKtTest : RoboActKtTemp() {
    @org.junit.Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        assertNotEquals(4, 2 + 1)

        assertNotNull(CTX_APP)
        assertNotNull(APPLICATION)
    }
}