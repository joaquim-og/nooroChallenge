package com.confradestech.noorochallenge.core.presentation.util

fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) {
        this[0].uppercaseChar() + this.substring(1).lowercase()
    } else {
        this
    }
}

fun String.sanitizeToValidHttps(): String {
    return if (this.startsWith("https")) {
        this
    } else {
        "https:$this"
    }
}