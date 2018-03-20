package utils

import com.google.gson.Gson
import java.io.Serializable

object GsonWrapper {
    val gson = Gson()
}

fun Serializable.encode() = GsonWrapper.gson.toJson(this, this.javaClass)

fun <T> decode(body: String, type: Class<T>) = GsonWrapper.gson.fromJson(body, type)
