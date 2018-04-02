package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.google.gson.annotations.SerializedName
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
class EthTokenPriceInfo (
    /**
     * Timestamp
     */
    @SerializedName("ts")
    var timestamp: Long? = null,

    /**
     * String of the date
     */
    @SerializedName("date")
    var dateString: String? = null,

    /**
     *
     */
    var hour: Long = 0,

    /**
     * What it opened the day at
     */
    var open: BigDecimal? = null,

    /**
     * What it closed the day at
     */
    var close: BigDecimal? = null,

    /**
     * The high for the day
     */
    var high: BigDecimal? = null,

    /**
     * The low for the day
     */
    var low: BigDecimal? = null,

    /**
     * The volume over the day
     */
    var volume: BigDecimal? = null,

    /**
     *
     */
    var volumeConverted: BigDecimal? = null,

    /**
     * The average for the day
     */
    var average: BigDecimal? = null,

    /**
     * The market cap for the day
     */
    var cap: BigDecimal? = null
)
