package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal

open class LooprDepthListItem : RealmObject() {

    /**
     * Depth price to buy 1 tokenA with tokenB, in terms of tokenB
     * Example output - 0.0008666300
     */
    var depthPrice: BigDecimal?
        get() {
            return mDepthPrice?.let { BigDecimal(it) }
        }
        set(value) {
            mDepthPrice = value.toString()
        }

    private var mDepthPrice: String? = null

    /**
     * Amount of tokenA at this depth
     * Example output - 10000.0000000000
     */
    var amtTokenA: BigDecimal?
        get() {
            return mAmtTokenA?.let { BigDecimal(it) }
        }
        set(value) {
            mAmtTokenA = value.toString()
        }

    private var mAmtTokenA: String? = null

    /**
     * Amount of tokenB at this depth
     * Example output - 8.6663000000
     */
    var amtTokenB: BigDecimal?
        get() {
            return mAmtTokenB?.let { BigDecimal(it) }
        }
        set(value) {
            mAmtTokenB = value.toString()
        }

    private var mAmtTokenB: String? = null

    /**
     * Custom class deserializer
     */
    class LooprDepthListItemDeserializer : JsonDeserializer<LooprDepthListItem> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprDepthListItem? {
            when (json.isJsonNull || json.isJsonPrimitive) {
                true -> return null
                false -> {
                    val depthListItem = LooprDepthListItem()
                    val depthListItemJson = json.asJsonArray

                    //TODO - find out through testing where possible errors could arise with these
                    depthListItemJson.get(0)?.let{
                        depthListItem.mDepthPrice = it.asString
                    }

                    depthListItemJson.get(1).let {
                        depthListItem.mAmtTokenA = it.asString
                    }

                    depthListItemJson.get(2).let {
                        depthListItem.mAmtTokenB = it.asString
                    }

                    return depthListItem
                }
            }
        }

    }

}