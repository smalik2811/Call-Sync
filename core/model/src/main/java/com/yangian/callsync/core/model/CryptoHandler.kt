package com.yangian.callsync.core.model

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class CryptoHandler(
    private val key: String
) {
    private val algorithmName: String = "AES"
    @SuppressLint("GetInstance")
    private val cipher: Cipher = Cipher.getInstance(algorithmName)
    private val secretKeySpec = SecretKeySpec(key.substring(0, 16).toByteArray(), algorithmName)

    init {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    }

    fun decrypt(encryptedText: String): String {
        val decryptedString = cipher.doFinal(Base64.getDecoder().decode(encryptedText))
        return String(decryptedString)
    }

}