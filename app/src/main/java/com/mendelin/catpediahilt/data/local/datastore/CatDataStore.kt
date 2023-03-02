package com.mendelin.catpediahilt.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CatDataStore @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "catDataStore")

    private fun <T> getPreferences(key: Preferences.Key<T>) = context.dataStore.getPreference(key)

    val catDontShowAgain: Flow<Boolean?> = getPreferences(CAT_BOOLEAN_VALUE)

    suspend fun setCatDontShowAgain(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[CAT_BOOLEAN_VALUE] = value
        }
    }

    val catCounter: Flow<Int?> = getPreferences(CAT_INT_VALUE)

    suspend fun setCatCounter(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[CAT_INT_VALUE] = value
        }
    }

    suspend fun incCatCounter() {
        context.dataStore.edit { preferences ->
            val counter = preferences[CAT_INT_VALUE] ?: 0
            preferences[CAT_INT_VALUE] = counter + 1
        }
    }

    companion object {
        private val CAT_INT_VALUE = intPreferencesKey("CAT_INT_VALUE")
        private val CAT_LONG_VALUE = longPreferencesKey("CAT_LONG_VALUE")
        private val CAT_BOOLEAN_VALUE = booleanPreferencesKey("CAT_BOOLEAN_VALUE")
    }
}

fun <T> DataStore<Preferences>.getPreference(key: Preferences.Key<T>): Flow<T?> {
    return this.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference -> preference[key] }
}