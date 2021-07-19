package com.example.myapplication.database

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


object AES {
    private val iv = "0000000000000000".toByteArray()
    @Throws(Exception::class)
    private fun decrypt(encrypted: String, seed: String): String {
        val keyb = seed.toByteArray(charset("utf-8"))
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val thedigest: ByteArray = md.digest(keyb)
        val skey = SecretKeySpec(thedigest, "AES")
        val dcipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        dcipher.init(Cipher.DECRYPT_MODE, skey, IvParameterSpec(iv))
        val clearbyte: ByteArray = dcipher.doFinal(
            DatatypeConverter
                .parseHexBinary(encrypted)
        )
        return String(clearbyte)
    }

    @Throws(Exception::class)
    fun encrypt(content: String, key: String): String {
        val input = content.toByteArray(charset("utf-8"))
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val thedigest: ByteArray = md.digest(key.toByteArray(charset("utf-8")))
        val skc = SecretKeySpec(thedigest, "AES")
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, skc, IvParameterSpec(iv))
        val cipherText = ByteArray(cipher.getOutputSize(input.size))
        var ctLength: Int = cipher.update(input, 0, input.size, cipherText, 0)
        ctLength += cipher.doFinal(cipherText, ctLength)
        return DatatypeConverter.printHexBinary(cipherText)
    }

    /*@Throws(Exception::class)
    @JvmStatic
    fun maincrypt(data:String) {
        val key = "izhevskonline"
        val cipher = encrypt(data, key)
        //val decipher = decrypt(cipher, key)
        println(cipher)
        //println(decipher)
    }*/
}