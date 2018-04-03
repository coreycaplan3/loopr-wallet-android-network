package com.loopring.looprwalletnetwork.models.ethplorer.eth

import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Get price history for the ERC20 token at the given address
 *
 * @author arknw229
 */
open class EthTokenPriceHistoryGrouped(
        /**
         * Grouped information about token price history
         */
        var history: EthTokenPriceHistoryGroupedInfo? = null
) : RealmObject()
