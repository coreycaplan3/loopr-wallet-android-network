package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestWrapper(
        val jsonrpc: String,
        val method: String,
        val param: Any,
        val id: Int) {
}