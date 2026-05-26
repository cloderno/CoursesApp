package com.yeldar.common.utils

import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toEpochMilli(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}