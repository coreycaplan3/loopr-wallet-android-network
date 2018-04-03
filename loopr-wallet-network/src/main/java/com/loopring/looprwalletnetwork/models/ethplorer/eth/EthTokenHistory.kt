package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo.EthTransactionOperationInfo
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Gets history of token operations for an address
 *
 * @author arknw229
 */
open class EthTokenHistory(
        /**
         * List of [EthTransactionOperationInfo] objects containing the token history
         */
        var operations: RealmList<EthTransactionOperationInfo>? = null
) : RealmObject()