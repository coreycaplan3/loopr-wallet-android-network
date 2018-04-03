package com.loopring.looprwalletnetwork.models.ethplorer.addressinfo

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTokenBalanceInfo
import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTokenInfo
import io.realm.RealmList
import io.realm.RealmObject

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
 *   countTxs:    # Total count of incoming and outgoing transactions (including creation one),
 * }
 * ```
 *
 * @author arknw229
 */
open class EthAddressInfo(
        /**
         * Address the data is on
         */
        var address: String? = null,

        /**
         * Eth specific information
         */
        @SerializedName("ETH")
        var eth: EtherInfo? = null,

        /**
         * Exists if specified address is a contract
         */
        var contractInfo: ContractInfo? = null,

        /**
         * Exists if specified address is a token contract address
         */
        var tokenInfo: EthTokenInfo? = null,
        /**
         * List of balances for each token
         */
        var tokens: RealmList<EthTokenBalanceInfo>? = null,

        /**
         * Total count of incoming and outgoing transactions (including creation one)
         */
        var countTxs: String? = null

) : RealmObject()
