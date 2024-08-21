package com.lbg.data.mapper

fun String.convertToOneDecimal(): String {
    val number = this.toDoubleOrNull() ?: return this
    return String.format("%.1f", number)
}