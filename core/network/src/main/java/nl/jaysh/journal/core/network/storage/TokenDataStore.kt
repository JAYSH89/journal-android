@file:Suppress("DEPRECATION")

package nl.jaysh.journal.core.network.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class TokenStorage @Inject constructor(@ApplicationContext private val context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    private var accessToken: String? = null

    fun saveTokenPair(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    fun clearTokens() {
        clearAccessToken()
        clearRefreshToken()
    }

    fun getAccessToken() = accessToken

    fun getRefreshToken() = preferences.getString(REFRESH_TOKEN_KEY, null)

    private fun saveAccessToken(token: String) {
        accessToken = token
    }

    private fun clearAccessToken() {
        accessToken = null
    }

    private fun saveRefreshToken(token: String) = preferences.edit() {
        putString(REFRESH_TOKEN_KEY, token)
    }

    private fun clearRefreshToken() = preferences.edit() {
        remove(REFRESH_TOKEN_KEY)
    }

    companion object {
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}
