package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestOrderList(
        val owner: String,
        val orderHash: String,
        val status: String,
        val side: String,
        val delegateAddress: String,
        val market: String,
        val pageIndex: Int,
        val pageSize: Int) {
}