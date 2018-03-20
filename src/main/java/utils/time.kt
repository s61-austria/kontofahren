package utils

import java.sql.Timestamp
import java.time.Instant

fun now() = Timestamp.from(Instant.now())
