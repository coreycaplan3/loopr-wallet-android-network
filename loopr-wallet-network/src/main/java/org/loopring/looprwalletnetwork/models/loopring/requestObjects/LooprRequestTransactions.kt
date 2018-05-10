package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestTransactions(
        val owner: String,
        val thxHash: String,
        val symbol: String,
        val status: String,
        val txType: String,
        val pageIndex: Int,
        val pageSize: Int
) {
}