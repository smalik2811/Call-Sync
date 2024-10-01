package com.yangian.callsync.core.model

import com.yangian.callsync.core.model.cryptography.CryptoHandler
import com.yangian.callsync.core.model.cryptography.Decrypter
import com.yangian.callsync.core.model.cryptography.Encrypter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.security.InvalidKeyException

class CryptographyTest {
    private lateinit var cryptoHandler: CryptoHandler
    private lateinit var encrypter: Encrypter
    private lateinit var decrypter: Decrypter

    @Before
    fun setup() {
        cryptoHandler = CryptoHandler()
    }

    @Test
    fun testValidEncryption() {
        val key = "8aa28d245f3a2d4628087cf93b30dfbb6e93de94b32e09e95c74964df6cb618d".chunked(2)
            .map { it.toInt(16).toByte() }.toByteArray()
        cryptoHandler.getEncrypter(encryptionKey = key)
    }

    @Test
    fun testValidDecryption() {
        val key = "9FQSSC8uLBvFxT0O0zrLC20HocEa5za7".toByteArray()
        cryptoHandler.getDecrypter(decryptionKey = key)
    }

    @Test
    fun testInvalidEncryptionKey() {
        assertThrows(InvalidKeyException::class.java) {
            val key = "9a7".toByteArray()
            cryptoHandler.getEncrypter(encryptionKey = key)
        }
    }

    @Test
    fun testInvalidDecryptionKey() {
        assertThrows(InvalidKeyException::class.java) {
            val key = "9034fjeff".toByteArray()
            cryptoHandler.getDecrypter(decryptionKey = key)
        }
    }

    @Test
    fun testEncryptionDecryption() {
        val key = "8aa28d245f3a2d4628087cf93b30dfbb6e93de94b32e09e95c74964df6cb618d".chunked(2)
            .map { it.toInt(16).toByte() }.toByteArray()
        val plainMessage = "This is the secret message."

        encrypter = cryptoHandler.getEncrypter(encryptionKey = key)
        val encryptedMessage = encrypter.getEncryptedString(plainMessage)

        decrypter = cryptoHandler.getDecrypter(decryptionKey = key)
        val decryptedMessage = decrypter.getDecryptedString(encryptedMessage)

        assertEquals(plainMessage, decryptedMessage)
    }
}