package dev.all4.algorithmSteps.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Livio Lopez on 11/20/20.
 */

inline fun <reified T> T.getFrom(source: Any): T? {
    return try {
        return this.encode(source.decode())
    } catch (e: Throwable) {
        null
    }
}

inline fun <reified T> T.decode(): String? {
    return try {
        Gson().toJson(this)
    } catch (e: Throwable) {
        null
    }
}

inline fun <reified T> T.encode(dataString: String?): T? {
    return if(dataString.isNullOrEmpty()) {
        null
    } else {
        Gson().encodeClass<T>(dataString)
    }
}

inline fun <reified T> Gson.encodeClass(json: String): T? {
    return try {
        return this.fromJson<T>(json, object: TypeToken<T>() {}.type)
    } catch (e: Throwable) {
        null
    }
}
