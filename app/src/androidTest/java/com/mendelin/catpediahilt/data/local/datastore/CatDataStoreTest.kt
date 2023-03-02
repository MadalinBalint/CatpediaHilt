package com.mendelin.catpediahilt.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CatDataStoreTest {
    private val TAG = CatDataStore::class.java.simpleName

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())

    @Before
    fun setUp() {
        Log.e(TAG, "@Before")
    }

    @After
    fun tearDown() {
        Log.e(TAG, "@After")
    }

    @Test
    fun test_dataStore() = testCoroutineScope.runTest {
        val store = CatDataStore(testContext)

        store.catCounter.test {
            store.setCatCounter(1)
            store.incCatCounter()

            assertThat(awaitItem()).isEqualTo(2)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
