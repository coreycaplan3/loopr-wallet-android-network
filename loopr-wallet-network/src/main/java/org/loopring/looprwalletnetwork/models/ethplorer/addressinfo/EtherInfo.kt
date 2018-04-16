package org.loopring.looprwalletnetwork.models.ethplorer.addressinfo

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
         * Ethereum balance
         */
        var balance: String? = null,

        /**
         * Total Ethereum in
         */
        var totalIn: String? = null,

        /**
         * Total Ethereum out
         */
        var totalOut: String? = null

) : RealmObject()