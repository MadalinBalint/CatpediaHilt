package com.mendelin.catpediahilt

import com.mendelin.catpediahilt.domain.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ResourceTest {
    @Test
    fun testSuccess() {
        val resource = Resource.Success(5)
        assertEquals(resource.data, 5)
        assertNull(resource.message)
    }

    @Test
    fun testLoading() {
        val resource = Resource.Loading(null)
        assertNull(resource.data)
        assertNull(resource.message)
    }

    @Test
    fun testError() {
        val resource = Resource.Error(null, "Error")
        assertNull(resource.data)
        assertEquals(resource.message, "Error")
    }
}