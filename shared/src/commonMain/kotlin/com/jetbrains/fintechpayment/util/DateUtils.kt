package com.jetbrains.fintechpayment.util

import kotlinx.datetime.Clock

fun getCurrentTimestamp(): Long {
    return Clock.System.now().toEpochMilliseconds()
}
