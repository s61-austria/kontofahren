package utils

import java.time.Instant
import java.util.Arrays
import java.time.Instant.ofEpochMilli


fun capped(instant: Instant): Instant {
    val instants = arrayOf<Instant>(Instant.ofEpochMilli(java.lang.Long.MIN_VALUE), instant, Instant.ofEpochMilli(java.lang.Long.MAX_VALUE))
    Arrays.sort(instants)
    return instants[1]
}
