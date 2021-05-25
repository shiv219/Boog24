package com.boog24.extra

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.boog24.R
import com.boog24.activity.SplaceActivity.AuthSuccess
import com.boog24.extra.Utils.TAG

class BiometricAuth {
    fun createBiometricPrompt(ctx: FragmentActivity, onSuccess: AuthSuccess): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(ctx)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "$errorCode :: $errString")
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
//                    loginWithPassword() // Because in this app, the negative button allows the user to enter an account password. This is completely optional and your app doesn’t have to do it.
                    onSuccess.OnError()

                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Authentication failed for an unknown reason")
                onSuccess.onFailure()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication was successful")
                // Proceed with viewing the private encrypted message.
//                showEncryptedMessage(result.cryptoObject)
                onSuccess.onSuccess()
            }
        }

        return BiometricPrompt(ctx, executor, callback)
    }

    fun createPromptInfo(context: Context): BiometricPrompt.PromptInfo {



        return BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.authentication))
                .setNegativeButtonText(context.getString(R.string.cancel))
                .setConfirmationRequired(false)
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()
    }
}