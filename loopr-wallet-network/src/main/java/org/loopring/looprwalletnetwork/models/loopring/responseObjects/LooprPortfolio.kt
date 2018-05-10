package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprPortfolio(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * A list of [LooprPortfolioToken] objects with token data
     */
    @SerializedName("result")
    var tokens : RealmList<LooprPortfolioToken>? = null

    /**
     * Custom class deserializer
     */
    class LooprPortfolioDeserializer : JsonDeserializer<LooprPortfolio> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprPortfolio? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val portfolioObj = LooprPortfolio()

                LooprResponse.checkForError(jsonObj)
                portfolioObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {

                    portfolioObj.tokens = RealmList()
                    it.asJsonArray.forEach {
                        portfolioObj.tokens?.add(context.deserialize(it, LooprPortfolioToken::class.java))
                    }
                }
                return portfolioObj
            }
        }

    }
}