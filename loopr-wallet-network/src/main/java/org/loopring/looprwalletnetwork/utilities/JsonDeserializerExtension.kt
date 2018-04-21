package org.loopring.looprwalletnetwork.utilities

import com.google.gson.JsonObject
import kotlin.reflect.KMutableProperty

/**
 * Created by Corey on 4/3/2018
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: To reduce the boilerplate necessary when using the *JsonDeserializer* class
 */

/**
 * Executes the given [block] with the key if the JSON key's value ([property]) is not null or
 * empty.
 */
inline fun JsonObject.ifNotNullOrEmpty(property: KMutableProperty<*>, block: (String) -> Unit) {
    ifNotNullOrEmpty(property.name, block)
}

inline fun JsonObject.ifNotNullOrEmpty(element: String, block: (String) -> Unit) {
    if (get(element) != null && get(element).isJsonPrimitive && get(element).asString.trim() != "") {
        block(element)
    }
}