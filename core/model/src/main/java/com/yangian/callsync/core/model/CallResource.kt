package com.yangian.callsync.core.model

import android.icu.util.Calendar

data class CallResource(
    val id: Long,
    val name: String?,
    val number: String,
    val timestamp: Long,
    val duration: Long,
    val type: Int,
) {
    override fun toString(): String {
        return "${id}U+0009${name}U+0009${number}U+0009${timestamp}U+0009${duration}U+0009${type}"
    }

    fun getDateString(): String {
        val calendarInstance = Calendar.getInstance()
        val currentYear = calendarInstance.get(Calendar.YEAR)
        val currentMonth = calendarInstance.get(Calendar.MONTH)
        val currentDay = calendarInstance.get(Calendar.DAY_OF_MONTH)

        calendarInstance.timeInMillis = timestamp

        var result = ""
        if (currentDay == calendarInstance.get(Calendar.DAY_OF_MONTH) && currentMonth == calendarInstance.get(
                Calendar.MONTH
            ) && currentYear == calendarInstance.get(Calendar.YEAR)
        ) {

            val meridian = when (calendarInstance.get(Calendar.AM_PM)) {
                0 -> "AM"
                1 -> "PM"
                else -> ""
            }
            val hour = when (calendarInstance.get(Calendar.HOUR)) {
                0 -> "12"
                else -> calendarInstance.get(Calendar.HOUR).toString().padStart(2, '0')
            }
            val minute = calendarInstance.get(Calendar.MINUTE).toString().padStart(2, '0')

            result = "$hour:$minute $meridian"
        } else {
            val monthName = when (calendarInstance.get(Calendar.MONTH)) {
                0 -> "Jan"
                1 -> "Feb"
                2 -> "Mar"
                3 -> "Apr"
                4 -> "May"
                5 -> "Jun"
                6 -> "Jul"
                7 -> "Aug"
                8 -> "Sep"
                9 -> "Oct"
                10 -> "Nov"
                11 -> "Dec"
                else -> ""
            }

            result = "$monthName ${calendarInstance.get(Calendar.DAY_OF_MONTH)}"

            if (calendarInstance.get(Calendar.YEAR) != currentYear) {
                result += ", ${calendarInstance.get(Calendar.YEAR)}"
            }
        }

        return result
    }

    fun getDurationString(): String {
        val hour = duration / 3600
        val minute = (duration % 3600) / 60
        val second = duration % 60

        return if (hour > 0) {
            "${hour}h ${minute}m ${second}s"
        } else if (minute > 0) {
            "${minute}m ${second}s"
        } else {
            "${second}s"
        }
    }

    companion object {
        const val DELIMETER = "U+0009"

        fun toObject(encryptedString: String, cryptoHandler: CryptoHandler): CallResource {
            val decryptedString = cryptoHandler.decrypt(encryptedString)
            val parts = decryptedString.split(DELIMETER)
            return CallResource(
                id = parts[0].toLong(),
                name = parts[1],
                number = parts[2],
                timestamp = parts[3].toLong(),
                duration = parts[4].toLong(),
                type = parts[5].toInt()
            )
        }
    }
}


//INCOMING_TYPE - 1 : Call log type for calls received by the user.
//OUTGOING_TYPE - 2 : Call log type for calls made by the user.
//MISSED_TYPE - 3 : Call log type for calls missed by the user.
//VOICEMAIL_TYPE - 4 : Call log type for calls made with a voicemail.
//REJECTED_TYPE - 5 : Call log type for calls rejected by the user.
//BLOCKED_TYPE - 6 : Call log type for calls blocked automatically.
//ANSWERED_EXTERNALLY_TYPE - 7 : Call log type for a call which was answered on another device. Used in situations where a call rings on multiple devices simultaneously and it ended up being answered on a device other than the current one.