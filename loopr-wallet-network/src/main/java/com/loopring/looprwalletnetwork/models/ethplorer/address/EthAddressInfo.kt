package com.loopring.looprwalletnetwork.models.ethplorer.address

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.ethplorer.tokens.EthTokenBalanceInfo
import com.loopring.looprwalletnetwork.models.ethplorer.tokens.EthTokenInfo

/**
 * Created by arknw229 on 3/12/18.
 *
 * Ethplorer API
 *
 * Returns list of address transactions
 *
 * limit:   maximum number of operations [1 - 50, default = 10]
 *
 * showZeroValues:  show transactions with zero ETH value, default = 0
 *
 * token: show balances for specified token address only
 *
 * ```
 * {
 *   address: # address,
 *   ETH: {   # ETH specific information
 *     balance:  # ETH balance
 *     totalIn:  # Total incoming ETH value
 *     totalOut: # Total outgoing ETH value
 *   },
 *   contractInfo: {  # exists if specified address is a contract
 *     creatorAddress:  # contract creator address,
 *     transactionHash: # contract creation transaction hash,
 *     timestamp:       # contract creation timestamp
 *   },
 *   tokenInfo:  # exists if specified address is a token contract address (same format as token info),
 *   tokens: [   # exists if specified address has any token balances
 *     {
 *       tokenInfo: # token data (same format as token info),
 *       balance:   # token balance (as is, not reduced to a floating point value),
 *       totalIn:   # total incoming token value
 *       totalOut:  # total outgoing token value
 *     },
 *     ...
 *   ],
 *   countTxs:    # Total count of incoming and outcoming transactions (including creation one),
 * }
 * ```
 *
 * @author arknw229
 */
class EthAddressInfo(
        /**
         * Address the data is on
         */
        val address: String? = null,

        /**
         * Eth specific information
         */
        @SerializedName("ETH")
        val eth: EthSpecificInfo? = null,

        /**
         * Exists if specified address is a contract
         */
        val contractInfo: ContractInfo? = null,

        /**
         * Exists if specified address is a token contract address
         */
        val tokenInfo: EthTokenInfo? = null,
        /**
         * List of balances for each token
         */
        val tokens: Array<EthTokenBalanceInfo>? = null,

        /**
         * Total count of incoming and outcoming transactions (including creation one)
         */
        val countTxs: String? = null

) {

    /**
     * Eth specific information
     *
     * ```
     * {
     *   balance:  # ETH balance
     *   totalIn:  # Total incoming ETH value
     *   totalOut: # Total outgoing ETH value
     * }
     * ```
     */
    class EthSpecificInfo(

            /**
             * Ethereum ballance
             */
            val balance: String? = null,

            /**
             * Total Ethereum in
             */
            val totalIn: String? = null,

            /**
             * Total Ethereum out
             */
            val totalOut: String? = null
    )

    /**
     * Exists if the address is for a contract
     *
     * ```
     * {
     *   creatorAddress:  # contract creator address,
     *   transactionHash: # contract creation transaction hash,
     *   timestamp:       # contract creation timestamp
     * }
     * ```
     */
    class ContractInfo(
            /**
             * Address of the creator of the contract
             */
            var creatorAddress: String? = null,

            /**
             * Contract creation hash
             */
            var transactionHash: String? = null,

            /**
             * Timestamp for the creation of the contract
             */
            var timestamp: String? = null
    )
}
