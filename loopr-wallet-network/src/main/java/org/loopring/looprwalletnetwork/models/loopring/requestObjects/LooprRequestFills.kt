package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestFills(
        val market: String,
        val owner: String,
        val delegateAddress: String,
        val orderHash: String,
        val ringHash: String,
        val pageIndex: Int,
        val pageSize: Int
) {
}