package com.company

import java.util.*

fun ByteArray?.fromByteArrayToHexString(): String? {
    return if (this == null) null
    else {
        val sb = StringBuilder()
        for (i in this.indices) sb.append(String.format("%02X", this[i]))
        sb.toString().uppercase(Locale.getDefault())
    }
}

fun String.fromHexStringToByteArray(): ByteArray {
    val s2 = this.trim { it <= ' ' }
    return when {
        s2.length % 2 != 0 -> throw Exception()
        s2.isEmpty() -> ByteArray(0)
        else -> {
            val array = ByteArray(s2.length / 2)
            var i = 0
            var j = 0
            while (j < s2.length) {
                array[i] = s2.substring(j, j + 2).toInt(16).toByte()
                i++
                j += 2
            }
            array
        }
    }
}