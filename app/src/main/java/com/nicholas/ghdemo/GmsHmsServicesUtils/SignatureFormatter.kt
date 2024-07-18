package com.nicholas.ghdemo.GmsHmsServicesUtils

class SignatureFormatter {

    fun hexStringToByteArray(s: String): ByteArray {
        val cleanedHex = s.replace(":", "")
        val len = cleanedHex.length
        val data = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            data[i / 2] = ((Character.digit(cleanedHex[i], 16) shl 4) + Character.digit(cleanedHex[i + 1], 16)).toByte()
        }
        return data
    }


}