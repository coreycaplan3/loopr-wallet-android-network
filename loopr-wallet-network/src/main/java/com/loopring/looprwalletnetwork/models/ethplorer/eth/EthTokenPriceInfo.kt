package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Information about an ERC20 token at a given time
 *
 * @author arknw229
 */
open class EthTokenPriceInfo : RealmObject() {

    /**
     * UNIX timestamp of when the price was last updated
     */
    @SerializedName("ts")
    var timestamp: Long? = null

    /**
     * String representation of the timestamp, in YYYY-MM-DD format
     */
    @SerializedName("date")
    var dateString: String? = null

    /**
     * From
     */
    var hour: Long = 0

    /**
     * What it opened the day at
     */
    var open: BigDecimal? = null

    /**
     * What it closed the day at
     */
    var close: BigDecimal? = null

    /**
     * The high for the day
     */
    var high: BigDecimal? = null

    /**
     * The low for the day
     */
    var low: BigDecimal? = null

    /**
     * The volume over the day
     */
    var volume: BigDecimal? = null

    /**
     *
     */
    var volumeConverted: BigDecimal? = null

    /**
     * The average for the day
     */
    var average: BigDecimal? = null

    /**
     * The market cap for the day
     */
    var cap: BigDecimal? = null

}
