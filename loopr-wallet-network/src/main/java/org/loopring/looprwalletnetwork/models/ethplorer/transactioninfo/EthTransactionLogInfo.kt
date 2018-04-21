package org.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Logged information about a transaction
 *
 * @author arknw229
 */
open class EthTransactionLogInfo(

        /**
         * Logged addresses
         */
        var address: String? = null,

        /**
         * Logged topics
         */
        var topics: RealmList<String>? = null,

        /**
         * Logged data
         */
        var data: String? = null
) : RealmObject()
