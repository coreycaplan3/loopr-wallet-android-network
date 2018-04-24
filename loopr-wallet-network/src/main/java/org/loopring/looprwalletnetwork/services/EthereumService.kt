package org.loopring.looprwalletnetwork.services

import kotlinx.coroutines.experimental.Deferred
import org.loopring.looprwalletnetwork.models.ethereum.EthBlockNum
import org.loopring.looprwalletnetwork.models.loopring.LooprBalance
import retrofit2.http.*

interface EthereumService {

    /**
     * Returns the number of most recent block
     *
     * @return [EthBlockNum]
     */
    @FormUrlEncoded
    @POST("/")
    fun getBalances(@Field("jsonrpc") jsonRpc: String,
                    @Field("method") method: String,
                    @Field("params") params: String,
                    @Field("id") id: String): Deferred<EthBlockNum>
}