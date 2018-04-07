package com.loopring.looprwalletnetwork.models.ethplorer.addressinfo

import io.realm.RealmObject

/**
 * Ethplorer API
 *
 * ETH specific information
 *
 * ```
 * {
 *   balance:  # ETH balance
 *   totalIn:  # Total incoming ETH value
 *   totalOut: # Total outgoing ETH value
 * }
 * ```
 *
 * @author arknw229
 */
open class EtherInfo(

        /**
         * Ethereum balance in eth
         */
        var balance: String? = null,

        /**
         * Total Ethereum sent into the address in eth
         */
        var totalIn: String? = null,

        /**
         * Total Ethereum sent out of the address in eth
         */
        var totalOut: String? = null

) : RealmObject()