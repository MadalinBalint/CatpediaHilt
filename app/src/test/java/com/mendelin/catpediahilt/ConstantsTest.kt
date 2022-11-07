package com.mendelin.catpediahilt

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConstantsTest {
    @Test
    fun testConstants() {
        assertEquals(BuildConfig.API_HEADER, "x-api-key")
        assertEquals(BuildConfig.API_KEY, "64f39d73-2f0a-4515-b192-7e5aa8e33ea2")
        assertEquals(BuildConfig.BASE_URL, "https://api.thecatapi.com/")
    }
}