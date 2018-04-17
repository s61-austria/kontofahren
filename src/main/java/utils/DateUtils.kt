package utils

import org.joda.time.DateTime
import java.time.Instant
import java.util.Arrays
import java.time.Instant.ofEpochMilli

fun capped(instant: Instant): Instant {
    val instants = arrayOf<Instant>(Instant.ofEpochMilli(java.lang.Long.MIN_VALUE), instant, Instant.ofEpochMilli(java.lang.Long.MAX_VALUE))
    Arrays.sort(instants)
    return instants[1]
}

infix fun DateTime.before(other: DateTime) = this.isBefore(other)
