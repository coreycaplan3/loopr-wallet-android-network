package org.loopring.looprwalletnetwork.utilities

import android.util.Log
import com.google.gson.JsonObject
import java.math.BigDecimal

/**
 * Created by Corey on 4/3/2018
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: For quickly parsing [BigDecimal] values
 */

inline fun <reified T> T.parseBigDecimal(jsonObject: JsonObject, key: String): BigDecimal? {
    return try {
        BigDecimal(jsonObject.get(key).asString)
    } catch (e: NumberFormatException) {
        Log.w(T::class.java.simpleName, "Warning: json element '$key' could not be parsed as a BigDecimal, variable set to null")
        null
    }
}