package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.loopring.looprwalletnetwork.models.etherscan.transactions.EthTransactionOperationInfo

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Gets history of token operations for an address
 *
 * @author arknw229
 */
class EthTokenHistory {
    /**
     * List of [EthTransactionOperationInfo] objects containing the token history
     */
    var operations: MutableList<EthTransactionOperationInfo>? = null
}
