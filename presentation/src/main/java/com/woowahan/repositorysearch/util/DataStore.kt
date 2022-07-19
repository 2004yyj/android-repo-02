package com.woowahan.repositorysearch.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import java.io.IOException

val tokenPrefsKey = stringPreferencesKey("token")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

suspend inline fun <T : Any> DataStore<Preferences>.set(key: Preferences.Key<T>, value: T) {
    edit { preferences ->
        preferences[key] = value
    }
}

suspend inline fun <T : Any> DataStore<Preferences>.get(key: Preferences.Key<T>, defaultValue: T): T {
    return data.catch { recoverOrThrow(it) }.map { it[key] }.firstOrNull() ?: defaultValue
}

suspend fun FlowCollector<Preferences>.recoverOrThrow(throwable: Throwable) {
    if (throwable is IOException) {
        emit(emptyPreferences())
    } else {
        throw throwable
    }
}