package com.mendelin

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.mendelin.catpediahilt.data.local.datastore.CatDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@Config(sdk = [31])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class CatDataStoreTest {
    private lateinit var context: Context
    private lateinit var store: CatDataStore

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        store = CatDataStore(context)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun empty_dataStore_counter_returns_null() = runTest {
        val item = store.catCounter.firstOrNull()
        assertThat(item).isEqualTo(null)
    }

    @Test
    fun empty_dataStore_flag_returns_null() = runTest {
        val item = store.catDontShowAgain.firstOrNull()
        assertThat(item).isEqualTo(null)
    }

    @Test
    fun dataStore_set_counter_returns_true() = runTest {
        store.setCatCounter(100)

        val item = store.catCounter.firstOrNull()
        assertThat(item).isEqualTo(100)
    }

    @Test
    fun dataStore_set_and_increment_counter_returns_true() = runTest {
        store.setCatCounter(1005)
        store.incCatCounter()

        val item = store.catCounter.firstOrNull()
        assertThat(item).isEqualTo(1006)
    }

    @Test
    fun dataStore_set_flag_returns_true() = runTest {
        store.setCatDontShowAgain(true)
        store.catDontShowAgain

        val item = store.catDontShowAgain.firstOrNull()
        assertThat(item).isTrue()
    }
}