package com.player.presentation.util

import java.util.Locale
import java.util.concurrent.TimeUnit

object Util {

    fun calculateDuration(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(
                hours
            )
        return String.format(Locale.ENGLISH, "%2d:%02d", minutes, seconds)
    }


    fun truncateName(name: String): String {
        val parathesisIndex = name.indexOf("(").takeIf { it != -1 } ?: Int.MAX_VALUE
        val bracketIndex = name.indexOf("[").takeIf { it != -1 } ?: Int.MAX_VALUE
        val index = minOf(parathesisIndex, bracketIndex)
        return if (index == Int.MAX_VALUE) name.trim() else name.take(index).trim()
    }

}