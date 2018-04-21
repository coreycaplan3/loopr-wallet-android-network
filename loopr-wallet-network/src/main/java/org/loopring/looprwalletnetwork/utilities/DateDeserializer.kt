package org.loopring.looprwalletnetwork.utilities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Corey Caplan on 4/3/18.
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: To standardize how [Date] objects are de-serialized by GSON
 */
class DateDeserializer : JsonDeserializer<Date> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        return when {
            json != null && json.isJsonPrimitive ->
                json.asString.toLongOrNull()?.let { Date(it) }
            else ->
                null
        }
    }

}