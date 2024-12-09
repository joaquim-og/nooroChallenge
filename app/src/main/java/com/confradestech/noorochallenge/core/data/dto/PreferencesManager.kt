package com.confradestech.noorochallenge.core.data.dto

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("app_preferences")

class PreferencesManager(private val context: Context) {

    private val lastCitySearched = stringPreferencesKey("last_city_searched")

    val getLastCity: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[lastCitySearched]
    }

    suspend fun saveLastCity(key: String) {
        context.dataStore.edit { preferences ->
            preferences[lastCitySearched] = key
        }
    }
}